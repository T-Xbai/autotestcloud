package com.port.testcloud.autotestcloud.service.impl;

import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.service.cases.TestCaseService;
import com.port.testcloud.autotestcloud.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestCaseServiceImplTest {

    @Autowired
    private TestCaseService caseService;

    @Test
    public void findOne() {
        TestCaseDto one = caseService.findOne("1");
        log.info(one.toString());
    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0,1);
        Page<TestCaseDto> testCaseDtoPage = caseService.findAll(request);
        log.info(testCaseDtoPage.getContent().toString());
    }

    @Test
    public void findAll1() {
        PageRequest request = new PageRequest(0,1);
        Page<TestCaseDto> testCaseDtoPage = caseService.findAll("44349331564383947521", request);
        log.info(testCaseDtoPage.getContent().toString());
    }

    @Test
    public void findByIndex() {
        TestCaseDto byIndex = caseService.findByIndex("44349331564383947533", 0);
        Assert.assertNotNull(byIndex);
    }

    @Test
    public void findByCaseName() {
        List<TestCaseDto> testCaseDtoList = caseService.findByCaseName("Login");
        Assert.assertTrue(testCaseDtoList.size() > 0);
    }



    @Test
    public void findByCaseName_1() {
        List<TestCaseDto> testCaseDtoList = caseService.findByCaseName("44349331564383947521","02");
        Assert.assertTrue(testCaseDtoList.size() > 0);
    }
    @Test
    public void save() {
        TestCaseDto caseDto = new TestCaseDto();
        caseDto.setId(KeyUtil.unique());
        caseDto.setCaseName("个人中心——02");
        caseDto.setModuleId("14309801564114860138");
        caseDto.setIndexs(1);

        TestCaseDto result = caseService.save(caseDto);
        Assert.assertNotNull(result);


    }
}