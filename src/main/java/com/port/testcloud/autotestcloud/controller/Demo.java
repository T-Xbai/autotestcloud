package com.port.testcloud.autotestcloud.controller;

import com.port.testcloud.autotestcloud.dto.InfoDto;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.service.RequestService;
import com.port.testcloud.autotestcloud.service.impl.RequestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @ClassName: Demo
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-01 10:41
 * @Description:
 */
@RestController
@RequestMapping("/demo")
@Slf4j
public class Demo {

    @Autowired
    private RequestService requestService;


    @GetMapping
    public String get(){

        TestCaseDto testCaseDto = new TestCaseDto();

        InfoDto infoDto = new InfoDto();
        infoDto.setRequestUrl("http://localhost:8889/auto/cloud/test/case/module?moduleId=70151411564326036094");
        infoDto.setRequestMethod("get");
        testCaseDto.setInfo(infoDto);

        String body = null;
        try {
            Response response = requestService.get(testCaseDto);
            assert response.body() != null;
            body = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;

    }


}
