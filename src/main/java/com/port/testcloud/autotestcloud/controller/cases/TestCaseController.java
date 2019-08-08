package com.port.testcloud.autotestcloud.controller.cases;

import com.port.testcloud.autotestcloud.VO.CaseInfoVO;
import com.port.testcloud.autotestcloud.VO.DbOperationVO;
import com.port.testcloud.autotestcloud.VO.DependCaseVO;
import com.port.testcloud.autotestcloud.VO.ResultVO;
import com.port.testcloud.autotestcloud.convert.testcase.CaseInfoFormToTestCaseDto;
import com.port.testcloud.autotestcloud.convert.testcase.TestCaseDtoToCaseInfoVO;
import com.port.testcloud.autotestcloud.domain.DbOperation;
import com.port.testcloud.autotestcloud.domain.DependCase;
import com.port.testcloud.autotestcloud.dto.InfoDto;
import com.port.testcloud.autotestcloud.dto.ModuleDto;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.form.CaseInfoForm;
import com.port.testcloud.autotestcloud.service.cases.CaseInfoService;
import com.port.testcloud.autotestcloud.service.cases.DbOperationService;
import com.port.testcloud.autotestcloud.service.cases.DependCaseService;
import com.port.testcloud.autotestcloud.service.projects.ProjectModuleService;
import com.port.testcloud.autotestcloud.service.cases.TestCaseService;
import com.port.testcloud.autotestcloud.utils.KeyUtil;
import com.port.testcloud.autotestcloud.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static com.port.testcloud.autotestcloud.convert.testcase.TestCaseDtoToCaseInfoVO.convert;

/**
 * @ClassName: TestCaseController
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-30 16:57
 * @Description: 用例
 */
@Slf4j
@RestController
@RequestMapping("/test/case")
public class TestCaseController {

    @Autowired
    private TestCaseService caseService;

    @Autowired
    private CaseInfoService infoService;

    @Autowired
    private ProjectModuleService moduleService;

    @Autowired
    private DependCaseService dependCaseService;

    @Autowired
    private DbOperationService dbOperationService;

    @GetMapping("/{caseId}")
    public ResultVO findOne(@PathVariable("caseId") String caseId) {
        CaseInfoVO infoVO = new CaseInfoVO();

        TestCaseDto testCaseDto = caseService.findOne(caseId);

        BeanUtils.copyProperties(testCaseDto.getInfo(), infoVO);
        BeanUtils.copyProperties(testCaseDto, infoVO);


        List<DependCaseVO> dependCaseVOList = new ArrayList<>();
        testCaseDto.getDependCaseList().forEach(dependCase -> {
            DependCaseVO dependCaseVO = new DependCaseVO();
            BeanUtils.copyProperties(dependCase, dependCaseVO);
            dependCaseVOList.add(dependCaseVO);
        });
        infoVO.setDependCaseList(dependCaseVOList);


        List<DbOperationVO> dbOperationVOList = new ArrayList<>();
        testCaseDto.getDbOperationList().forEach(dbOperation -> {
            DbOperationVO dbOperationVO = new DbOperationVO();
            BeanUtils.copyProperties(dbOperation, dbOperationVO);
            dbOperationVOList.add(dbOperationVO);

        });
        infoVO.setDbOperationList(dbOperationVOList);

        return ResultVOUtil.success(infoVO);
    }


    @GetMapping
    public ResultVO searchCaseName(@RequestParam(name = "moduleId", required = false) String moduleId,
                                   @RequestParam("caseName") String caseName) {

        List<TestCaseDto> byCaseName;
        if (moduleId == null) {
            byCaseName = caseService.findByCaseName(caseName);
        } else {

            ModuleDto moduleDto = moduleService.findOne(moduleId);
            if (moduleDto == null || moduleDto.getId() == null) {
                log.error("【用例搜索】moduleId 不存在：{}", moduleId);
                throw new AutoTestException(ResultEnums.MODULE_NOT_EXIST);
            }
            byCaseName = caseService.findByCaseName(moduleId, caseName);
        }

        byCaseName.sort(Comparator.comparing(TestCaseDto::getIndexs));
        return ResultVOUtil.success(byCaseName);
    }


    /**
     * 查询模块下所有case - 分页
     *
     * @return
     */
    @GetMapping("/module")
    public ResultVO findAllByModuleId(@RequestParam("moduleId") String moduleId,
                                      @PageableDefault Pageable pageable) {
        ModuleDto moduleDto = moduleService.findOne(moduleId);
        if (moduleDto == null || moduleDto.getId() == null) {
            log.error("【用例搜索】moduleId 不存在：{}", moduleId);
            throw new AutoTestException(ResultEnums.MODULE_NOT_EXIST);
        }

        Page<TestCaseDto> testCaseDtoPage = caseService.findAll(moduleId, pageable);
        List<CaseInfoVO> caseInfoVOS = convert(testCaseDtoPage.getContent());
        caseInfoVOS.sort(Comparator.comparing(CaseInfoVO::getIndexs));
        return ResultVOUtil.success(caseInfoVOS, testCaseDtoPage);
    }


    /**
     * 创建用例
     */
    @PostMapping
    @Transactional
    public ResultVO create(@RequestBody @Valid CaseInfoForm form, BindingResult result) {

        if (result.hasErrors()) {
            log.error("【用例创建】参数错误：{}", result.getFieldError().getDefaultMessage());
            throw new AutoTestException(ResultEnums.PROJECT_FORM_ERROR);
        }

        // 创建 case
        TestCaseDto testCaseDto = CaseInfoFormToTestCaseDto.convert(form);
        final String caseId = KeyUtil.unique();
        testCaseDto.setId(caseId);
        caseService.save(testCaseDto);

        // 创建 case 详情
        InfoDto infoDto = testCaseDto.getInfo();
        infoDto.setId(KeyUtil.unique());
        infoDto.setCaseId(testCaseDto.getId());
        infoService.save(infoDto);

        // 创建依赖case
        testCaseDto.getDependCaseList().forEach(dependCase -> {
            dependCase.setId(KeyUtil.unique());
            dependCase.setCaseId(caseId);
            dependCaseService.save(dependCase);
        });


        // 创建db操作
        testCaseDto.getDbOperationList().forEach(dbOperation -> {
            dbOperation.setId(KeyUtil.unique());
            dbOperation.setCaseId(caseId);
            dbOperationService.save(dbOperation);
        });





        Map<String, String> responseResult = new HashMap<>();
        responseResult.put("id", testCaseDto.getId());
        return ResultVOUtil.success(responseResult);


    }


    @PutMapping("/{caseId}")
    @Transactional
    public ResultVO update(@PathVariable("caseId") String caseId,
                           @RequestBody @Valid CaseInfoForm form,
                           BindingResult result) {

        if (result.hasErrors()) {
            log.error("【用例编辑】参数错误：{}", result.getFieldError().getDefaultMessage());
            throw new AutoTestException(ResultEnums.PROJECT_FORM_ERROR);
        }


        TestCaseDto testCaseDto = caseService.findOne(caseId);
        testCaseDto = CaseInfoFormToTestCaseDto.convert(form, testCaseDto);

        caseService.save(testCaseDto);


        infoService.save(testCaseDto.getInfo());

        List<DependCase> dependCaseList = testCaseDto.getDependCaseList();
        dependCaseList.forEach(dependCase -> {
            if (!StringUtils.isEmpty(dependCase.getId())) {
                dependCaseService.isExist(dependCase.getId());
            } else {
                dependCase.setId(KeyUtil.unique());
            }

            dependCase.setCaseId(caseId);
            dependCaseService.save(dependCase);
        });


        List<DbOperation> dbOperationList = testCaseDto.getDbOperationList();
        dbOperationList.forEach(dbOperation -> {
            if (!StringUtils.isEmpty(dbOperation.getId())) {
                dbOperationService.isExist(dbOperation.getId());
            } else {
                dbOperation.setId(KeyUtil.unique());
            }

            dbOperation.setCaseId(caseId);
            dbOperationService.save(dbOperation);
        });


        CaseInfoVO infoVO = TestCaseDtoToCaseInfoVO.convert(testCaseDto);
        return ResultVOUtil.success(infoVO);
    }


    @DeleteMapping("/{caseId}")
    public ResultVO delelte(@PathVariable("caseId") String caseId) {
        caseService.delete(caseId);
        return ResultVOUtil.success("ok");
    }

}