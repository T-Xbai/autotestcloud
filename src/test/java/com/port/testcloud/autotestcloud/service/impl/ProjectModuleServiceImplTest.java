package com.port.testcloud.autotestcloud.service.impl;

import com.port.testcloud.autotestcloud.dto.ModuleDto;
import com.port.testcloud.autotestcloud.service.ProjectModuleService;
import com.port.testcloud.autotestcloud.utils.JsonUtil;
import com.port.testcloud.autotestcloud.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProjectModuleServiceImplTest {

    @Autowired
    private ProjectModuleService moduleService;

    @Test
    public void findOne() {
    }


    @Test
    public void sonAll(){
        List<ModuleDto> moduleDtoList = moduleService.sonAll("34087891564112036971");
        log.info("{}",moduleDtoList);
    }

//    @Test
//    public void findByProjectIdAndIsDelete() throws IOException {
//        List<ModuleDto> byProjectIdAndIsDelete = moduleService.findByProjectIdAndIsDelete("80186151564043751800", -1);
//        Assert.assertTrue(byProjectIdAndIsDelete.size() > 0);
//    }

    @Test
    public void findByParentIdAndIsDelete() {
    }

    @Test
//    @Transactional
    public void save() {
        for (int i = 0; i <10 ; i++) {
            ModuleDto moduleDto = new ModuleDto();
            moduleDto.setId(KeyUtil.unique());
            moduleDto.setParentId("15795991564324204096");
            moduleDto.setProjectId("80186151564043751800");
            moduleDto.setIndexs(i);
            moduleDto.setModuleName("username:".concat(String.valueOf(i)));
            ModuleDto result = moduleService.save(moduleDto);
            Assert.assertNotNull(result);
        }



    }
}