package com.port.testcloud.autotestcloud.controller;

import com.port.testcloud.autotestcloud.VO.ResultVO;
import com.port.testcloud.autotestcloud.domain.DbConfig;
import com.port.testcloud.autotestcloud.form.DbConfigForm;
import com.port.testcloud.autotestcloud.service.DbConfigService;
import com.port.testcloud.autotestcloud.utils.FormUtil;
import com.port.testcloud.autotestcloud.utils.KeyUtil;
import com.port.testcloud.autotestcloud.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName: DbConfigController
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-05 15:21
 * @Description:
 */

@Slf4j
@RestController
@RequestMapping("/db/config")
public class DbConfigController {

    @Autowired
    private DbConfigService dbConfigService;


    @GetMapping("/{id}")
    public ResultVO findOne(@PathVariable("id") String id) {
        dbConfigService.isExist(id);
        DbConfig dbConfig = dbConfigService.findOne(id);
        return ResultVOUtil.success(dbConfig);
    }


    @GetMapping
    public ResultVO findAll() {
        List<DbConfig> dbConfigList = dbConfigService.findAll();
        return ResultVOUtil.success(dbConfigList);
    }


    @PostMapping
    @Transactional
    public ResultVO create(@RequestBody @Valid DbConfigForm form, BindingResult result) {
        FormUtil.hasError(result);
        DbConfig dbConfig = new DbConfig();
        BeanUtils.copyProperties(form, dbConfig);
        dbConfig.setId(KeyUtil.unique());
        dbConfigService.save(dbConfig);
        return ResultVOUtil.success();
    }

    @PutMapping("{id}")
    @Transactional
    public ResultVO update(@PathVariable("id") String id,
                           @RequestBody @Valid DbConfigForm form,
                           BindingResult result) {

        FormUtil.hasError(result);
        dbConfigService.isExist(id);
        DbConfig dbConfig = new DbConfig();
        BeanUtils.copyProperties(form, dbConfig);
        dbConfig.setId(id);
        dbConfigService.save(dbConfig);
        return ResultVOUtil.success();
    }


}
