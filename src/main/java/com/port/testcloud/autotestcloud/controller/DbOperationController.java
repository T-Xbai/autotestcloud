package com.port.testcloud.autotestcloud.controller;

import com.port.testcloud.autotestcloud.VO.DbOperationVO;
import com.port.testcloud.autotestcloud.VO.ResultVO;
import com.port.testcloud.autotestcloud.domain.DbOperation;
import com.port.testcloud.autotestcloud.form.DbOperationForm;
import com.port.testcloud.autotestcloud.service.DbConfigService;
import com.port.testcloud.autotestcloud.service.cases.DbOperationService;
import com.port.testcloud.autotestcloud.service.cases.TestCaseService;
import com.port.testcloud.autotestcloud.utils.FormUtil;
import com.port.testcloud.autotestcloud.utils.KeyUtil;
import com.port.testcloud.autotestcloud.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName: DbOperationController
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-02 15:41
 * @Description:
 */
@RestController
@RequestMapping("/db/operation")
public class DbOperationController {

    @Autowired
    private DbOperationService dbOperationService;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private DbConfigService dbConfigService;

    @PostMapping
    @Transactional
    public ResultVO create(@RequestBody @Valid DbOperationForm form, BindingResult result) {
        FormUtil.hasError(result);
        testCaseService.isExist(form.getCaseId());
        dbConfigService.isExist(form.getDbConfigId());

        DbOperation dbOperation = new DbOperation();
        BeanUtils.copyProperties(form, dbOperation);
        dbOperation.setId(KeyUtil.unique());
        dbOperationService.save(dbOperation);
        return ResultVOUtil.success(dbOperation);
    }


    @PutMapping
    @Transactional
    public ResultVO update(@RequestParam("id") String id,
                           @RequestBody @Valid DbOperationForm form,
                           BindingResult result) {

        FormUtil.hasError(result);
        testCaseService.isExist(form.getCaseId());
        dbConfigService.isExist(form.getDbConfigId());
        dbOperationService.isExist(id);
        DbOperation dbOperation = dbOperationService.findOne(id);

        BeanUtils.copyProperties(form, dbOperation);
        dbOperation.setId(id);
        dbOperationService.save(dbOperation);
        return ResultVOUtil.success(dbOperation);
    }


    @GetMapping("/all")
    public ResultVO caseIdByall(@RequestParam("caseId") String caseId) {
        testCaseService.isExist(caseId);
        List<DbOperation> byCaseId = dbOperationService.findByCaseId(caseId);
        return ResultVOUtil.success(byCaseId);
    }



    @GetMapping
    public ResultVO findOne(@RequestParam("id") String id) {
        dbOperationService.isExist(id);
        DbOperation dbOperation = dbOperationService.findOne(id);
        return ResultVOUtil.success(dbOperation);
    }



    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable("id") String id) {

        dbOperationService.isExist(id);
        dbOperationService.delete(id);
        return ResultVOUtil.success();
    }


}
