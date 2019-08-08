package com.port.testcloud.autotestcloud.service.cases.impl;

import com.port.testcloud.autotestcloud.domain.RunResult;
import com.port.testcloud.autotestcloud.dto.InfoDto;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.service.cases.RunCaseService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RunCaseServiceImplTest {

    @Autowired
    private RunCaseService runCaseService;

    @Test
    public void runCase() throws IOException {
        TestCaseDto testCaseDto = new TestCaseDto();

        InfoDto infoDto = new InfoDto();
        infoDto.setRequestUrl("http://localhost:8889/auto/cloud/test/case/module?moduleId=70151411564326036094");
        infoDto.setRequestMethod("get");
        testCaseDto.setInfo(infoDto);

        RunResult response = runCaseService.runCase("");
        assert response.getResponseBody() != null;
        log.info(response.getResponseBody().toString());

    }
}