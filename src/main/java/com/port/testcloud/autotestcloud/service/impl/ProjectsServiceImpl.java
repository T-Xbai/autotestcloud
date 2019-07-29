package com.port.testcloud.autotestcloud.service.impl;

import com.port.testcloud.autotestcloud.domain.Projects;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.repository.ProjectsRepository;
import com.port.testcloud.autotestcloud.service.ProjectsService;
import com.port.testcloud.autotestcloud.utils.JsonUtil;
import com.port.testcloud.autotestcloud.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @ClassName: ProjectsServiceImpl
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-24 23:40
 * @Description:
 */
@Service
@Slf4j
public class ProjectsServiceImpl implements ProjectsService {

    @Autowired
    private ProjectsRepository repository;

    @Override
    public Projects findOne(String projectId) {
        return repository.findById(projectId).orElse(new Projects());
    }

    @Override
    public List<Projects> findAll() {
        // TODO 可以过滤已删除的
        return repository.findAll();
    }

    @Override
    public List<Projects> findByProjectNameLike(String projectName) {
        return repository.findByProjectNameContaining(projectName);
    }

    @Override
    public Projects save(Projects projects) {

        Projects queryProjectNameResult = repository.findByProjectName(projects.getProjectName());
        if (queryProjectNameResult != null) {
            log.error("【项目处理】项目名称已存在， projectName = {} ,queryResult = {}",
                    projects.getProjectName(), queryProjectNameResult);
            throw new AutoTestException(ResultEnums.PROJECT_NAME_IS_EXIST);
        }
        return repository.save(projects);
    }


}
