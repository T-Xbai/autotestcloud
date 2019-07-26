package com.port.testcloud.autotestcloud.service.impl;

import com.port.testcloud.autotestcloud.domain.Projects;
import com.port.testcloud.autotestcloud.service.ProjectsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProjectsServiceImplTest {

    @Autowired
    private ProjectsService projectsService;

    @Test
    public void findOne() {
        Projects one = projectsService.findOne("project:000001");
        log.info(one.toString());


    }

    @Test
    public void findAll() {
    }

    @Test
    public void findByProjectNameLike() {
    }

    @Test
    public void save() {
    }
}