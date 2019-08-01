package com.port.testcloud.autotestcloud.service.impl;

import com.port.testcloud.autotestcloud.dto.InfoDto;
import com.port.testcloud.autotestcloud.service.cases.CaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CaseInfoServiceImplTest {

    @Autowired
    private CaseInfoService infoService;

    @Test
    public void findOne() {
    }

    @Test
    public void findByCaseId() {
        InfoDto byCaseId = infoService.findByCaseId("3037123156446899024");
        Assert.assertNotNull(byCaseId);

    }

    @Test
    public void save() {
        InfoDto infoDto = new InfoDto();
        infoDto.setId("58544461564480228354");
        infoDto.setCaseId("30371231564468990254");
        infoDto.setRequestUrl("http://www.baidu.com");
        infoDto.setRequestMethod("POST");
        infoDto.setRequestBody("{\"username\":\"tm-bai\"}");
        infoDto.setRequestHeaders("{\"Content-Type\":\"application/json\"}");
        infoDto.setCheckResult("{\"$.username\":\"tm-bai\"}");

        InfoDto result = infoService.save(infoDto);
        Assert.assertNotNull(result);
    }
}