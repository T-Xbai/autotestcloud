package com.port.testcloud.autotestcloud.controller;

import com.port.testcloud.autotestcloud.VO.ResultVO;
import com.port.testcloud.autotestcloud.service.cases.DbOperationService;
import com.port.testcloud.autotestcloud.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable("id") String id){

        dbOperationService.isExist(id);
        dbOperationService.delete(id);
        return ResultVOUtil.success();
    }

}
