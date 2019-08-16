package com.port.testcloud.autotestcloud.service.cases.impl;

import com.port.testcloud.autotestcloud.domain.DbOperation;
import com.port.testcloud.autotestcloud.domain.DependCase;
import com.port.testcloud.autotestcloud.domain.TestCase;
import com.port.testcloud.autotestcloud.dto.InfoDto;
import com.port.testcloud.autotestcloud.dto.ModuleDto;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.repository.cases.TestCaseRepository;
import com.port.testcloud.autotestcloud.service.cases.CaseInfoService;
import com.port.testcloud.autotestcloud.service.cases.DbOperationService;
import com.port.testcloud.autotestcloud.service.cases.DependCaseService;
import com.port.testcloud.autotestcloud.service.projects.ProjectModuleService;
import com.port.testcloud.autotestcloud.service.cases.TestCaseService;
import com.port.testcloud.autotestcloud.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static com.port.testcloud.autotestcloud.convert.testcase.TestCaseToTestCaseDto.convert;
import static com.port.testcloud.autotestcloud.enums.DeleteStatusEnums.DEL;
import static com.port.testcloud.autotestcloud.enums.DeleteStatusEnums.NORMAL;

/**
 * @ClassName: TestCaseServiceImpl
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-30 00:12
 * @Description:
 */
@Service
@Slf4j
public class TestCaseServiceImpl implements TestCaseService {

    @Autowired
    private TestCaseRepository caseRepository;

    @Autowired
    private ProjectModuleService moduleService;

    @Autowired
    private CaseInfoService infoService;

    @Autowired
    private DependCaseService dependCaseService;

    @Autowired
    private DbOperationService dbOperationService;

    @Override
    public TestCaseDto findOne(String caseId) {

        isExist(caseId);
        TestCase testCase = caseRepository.findById(caseId).orElse(new TestCase());
        TestCaseDto testCaseDto = convert(testCase);

        InfoDto infoDto = infoService.findByCaseId(caseId);
        testCaseDto.setInfo(infoDto);

        List<DependCase> dependCaseList = dependCaseService.findByCaseId(caseId);
        testCaseDto.setDependCaseList(dependCaseList);

        List<DbOperation> dbOperationList = dbOperationService.findByCaseId(caseId);
        testCaseDto.setDbOperationList(dbOperationList);

        return testCaseDto;
    }

    @Override
    public Page<TestCaseDto> findAll(String moduleId, Pageable pageable) {
        Page<TestCase> testCasePage = caseRepository.findByModuleIdAndIsDelete(moduleId, NORMAL.getCode(), pageable);
        return convert(testCasePage, pageable);
    }

    @Override
    public Page<TestCaseDto> findAll(Pageable pageable) {
        Page<TestCase> testCasePage = caseRepository.findAllByIsDelete(NORMAL.getCode(), pageable);
        return convert(testCasePage, pageable);
    }

    @Override
    public List<TestCaseDto> findAll(String moudleId) {
        List<String> caseIdList = caseRepository.findByModuleToAllCaseId(moudleId, NORMAL.getCode());

        List<TestCaseDto> testCaseDtoList = new ArrayList<>();
        caseIdList.forEach(caseId->{
            TestCaseDto testCaseDto = this.findOne(caseId);
            testCaseDtoList.add(testCaseDto);
        });
        return testCaseDtoList;
    }


    @Override
    public TestCaseDto findByIndex(String moduleId, Integer indexs) {
        TestCase testCase = caseRepository.findByModuleIdAndIndex(moduleId, indexs, NORMAL.getCode());
        return convert(testCase);
    }

    @Override
    public List<TestCaseDto> findByCaseName(String caseName) {
        List<TestCase> testCaseList = caseRepository.findByCaseName(caseName, NORMAL.getCode());
        return convert(testCaseList);
    }

    @Override
    public List<TestCaseDto> findByCaseName(String moduleId, String caseName) {
        List<TestCase> testCaseList = caseRepository.findByCaseName(moduleId, caseName, NORMAL.getCode());
        return convert(testCaseList);
    }


    @Override
    public void delete(String caseId) {
        TestCase testCase = caseRepository.findById(caseId).orElse(new TestCase());
        this.isExist(caseId);
        testCase.setIsDelete(DEL.getCode());
        caseRepository.save(testCase);
    }

    @Override
    public void isExist(String caseId) {
        try {
            TestCase testCase = caseRepository.findById(caseId).get();
        }catch (NoSuchElementException e){
            log.error("【用例查询】caseId 不存在：{}", caseId);
            throw new AutoTestException(ResultEnums.TEST_CASE_NOT_EXIST);
        }
    }

    /**
     * TODO save 这块建议重构， 区分创建与修改，并且整合 info 表数据
     *
     * @param testCaseDto
     * @return
     */
    @Override
    public TestCaseDto save(TestCaseDto testCaseDto) {

        // 依赖模块必须存在
        ModuleDto moduleDto = moduleService.findOne(testCaseDto.getModuleId());
        if (moduleDto == null || moduleDto.getId() == null || moduleDto.getIsDelete().equals(DEL.getCode())) {
            log.error("【用例操作】moduleId 不存在: {} , qeuryResult = {}", testCaseDto.getModuleId(), moduleDto);
            throw new AutoTestException(ResultEnums.MODULE_NOT_EXIST);
        }
        // 依赖模块下index值不得重复
        TestCaseDto byIndex = findByIndex(testCaseDto.getModuleId(), testCaseDto.getIndexs());
        if (byIndex != null && !byIndex.getId().equals(testCaseDto.getId())) {
            log.error("【用例操作】index 重复: {} , qeuryResult = {}", testCaseDto.getIndexs(), byIndex);
            throw new AutoTestException(ResultEnums.INDEX_NOT_REPETITION);
        }


        TestCase testCase = new TestCase();
        BeanUtils.copyProperties(testCaseDto, testCase);
        caseRepository.save(testCase);

        return testCaseDto;
    }



}
