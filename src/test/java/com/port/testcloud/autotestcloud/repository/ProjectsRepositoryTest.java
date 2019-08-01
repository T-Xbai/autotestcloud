package com.port.testcloud.autotestcloud.repository;

import com.port.testcloud.autotestcloud.domain.Projects;
import com.port.testcloud.autotestcloud.repository.projects.ProjectsRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectsRepositoryTest {

    @Autowired
    private ProjectsRepository repository;

    @Test
    public void save() {

        final String id = "project:000002";
        Projects projects = new Projects();
        projects.setId(id);
        projects.setProjectName("踏瑞云");
        projects.setCreateUser("admin");
        Projects result = repository.save(projects);
        Assert.assertEquals(result.getId(),id);
    }

    @Test
    public void findByProjectNameLike(){
        List<Projects> byProjectNameLike = repository.findByProjectNameContaining("闪电购");
        Assert.assertTrue(byProjectNameLike.size() > 1);
    }

}