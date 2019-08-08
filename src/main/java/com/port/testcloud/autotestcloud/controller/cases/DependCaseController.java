package com.port.testcloud.autotestcloud.controller.cases;

import com.port.testcloud.autotestcloud.VO.ResultVO;
import com.port.testcloud.autotestcloud.service.cases.DependCaseService;
import com.port.testcloud.autotestcloud.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: DependCaseController
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-02 15:38
 * @Description:
 */
@RestController
@RequestMapping("/depend/case")
@Slf4j
public class DependCaseController {

    @Autowired
    private DependCaseService dependCaseService;


    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable("id") String id){
        dependCaseService.isExist(id);
        dependCaseService.delete(id);
        return ResultVOUtil.success();
    }


}
