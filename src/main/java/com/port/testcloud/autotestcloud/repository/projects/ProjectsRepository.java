package com.port.testcloud.autotestcloud.repository.projects;

import com.port.testcloud.autotestcloud.domain.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName: ProjectsRepository
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-24 23:25
 * @Description: 项目
 */
public interface ProjectsRepository extends JpaRepository<Projects, String> {

    List<Projects>findByProjectNameContaining(String projectName);

    Projects findByProjectName(String projectName);

}
