package com.port.testcloud.autotestcloud.service;

import com.port.testcloud.autotestcloud.domain.Projects;

import java.util.List;

/**
 * @ClassName: ProjectsService
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-24 23:33
 * @Description: 项目
 */
public interface ProjectsService {

    /**
     * 根据 id 查询
     */
    Projects findOne(String projectId);

    /**
     * 查询所有项目
     */
    List<Projects> findAll();

    /**
     * 根据项目名称模糊查询
     */
    List<Projects> findByProjectNameLike(String projectName);


    /**
     * 保存以及更新操作
     */
    Projects save(Projects projects);




}
