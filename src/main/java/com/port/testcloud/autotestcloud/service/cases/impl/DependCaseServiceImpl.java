package com.port.testcloud.autotestcloud.service.cases.impl;

import com.port.testcloud.autotestcloud.domain.DependCase;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.repository.cases.DependCaseRepository;
import com.port.testcloud.autotestcloud.service.cases.DependCaseService;
import com.port.testcloud.autotestcloud.service.cases.TestCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @ClassName: DependCaseServiceImpl
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-01 14:20
 * @Description: 依赖case
 */
@Service
@Slf4j
public class DependCaseServiceImpl implements DependCaseService {

    @Autowired
    private DependCaseRepository dependCaseRepository;

    @Autowired
    private TestCaseService testCaseService;

    @Override
    public List<DependCase> findByCaseId(String caseId) {
        return dependCaseRepository.findByCaseId(caseId);
    }

    @Override
    public DependCase save(DependCase dependCase) {
        testCaseService.isExist(dependCase.getCaseId());
        dependCaseRepository.save(dependCase);
        return dependCase;
    }

    @Override
    public void delete(String id) {
        dependCaseRepository.deleteById(id);
    }

    @Override
    public DependCase isExist(String id) {
        DependCase dependCase;
        try {
            dependCase = dependCaseRepository.findById(id).get();
        } catch (NoSuchElementException e){
            log.error("【依赖数据查询】dependCaseId 不存在：{}",id);
            throw new AutoTestException(ResultEnums.DEPEND_DATA_NOT_EXIST);
        }
        return dependCase;
    }
}
