package com.port.testcloud.autotestcloud.service.impl;

import com.port.testcloud.autotestcloud.domain.ProjectModules;
import com.port.testcloud.autotestcloud.domain.Projects;
import com.port.testcloud.autotestcloud.dto.ModuleDto;
import com.port.testcloud.autotestcloud.enums.DeleteStatusEnums;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.repository.ProjectModulesRepository;
import com.port.testcloud.autotestcloud.repository.ProjectsRepository;
import com.port.testcloud.autotestcloud.service.ProjectModuleService;
import com.port.testcloud.autotestcloud.service.ProjectsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ProjectModuleServiceImpl
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-25 18:25
 * @Description: 项目模块
 */
@Service
@Slf4j
public class ProjectModuleServiceImpl implements ProjectModuleService {


    @Autowired
    private ProjectModulesRepository modulesRepository;

    @Autowired
    private ProjectsService projectsService;


    @Override
    public ModuleDto findOne(String id) {
        return null;
    }

    @Override
    public List<ModuleDto> findByProjectIdAndIsDelete(String projectId, Integer isDelete) {
        return null;
    }

    @Override
    public List<ModuleDto> findByParentIdAndIsDelete(String projectId, Integer isDelete) {
        return null;
    }

    @Override
    public ModuleDto save(ModuleDto moduleDto) {

        Projects queryProject = projectsService.findOne(moduleDto.getProjectId());
        if (queryProject == null || queryProject.getIsDelete().equals(DeleteStatusEnums.DEL.getCode())) {
            log.error("【模块处理】projectId 异常：projectId = {}", moduleDto.getProjectId());
            throw new AutoTestException(ResultEnums.PROJECT_ERROR);
        }

        ProjectModules parentModule = modulesRepository.findById(moduleDto.getParentId()).get();

        if (parentModule == null || parentModule.getIsDelete().equals(DeleteStatusEnums.DEL.getCode())) {
            log.error("【模块处理】父级模块异常：parentId = {}", moduleDto.getParentId());
            throw new AutoTestException(ResultEnums.MODULE_ERROR);
        }

        List<ProjectModules> byParentIdAndIndex = modulesRepository.findByParentIdAndIndex(
                moduleDto.getParentId(), moduleDto.getIndex());

        if (byParentIdAndIndex != null && byParentIdAndIndex.size() > 0) {
            log.error("【模块处理】同一个父模块下，排序不可重复：parentId = {} , index = {}",
                    moduleDto.getParentId(), moduleDto.getIndex());
            throw new AutoTestException(ResultEnums.MODULE_INDEX_NOT_REPETITION);
        }

        ProjectModules projectModules = new ProjectModules();
        BeanUtils.copyProperties(moduleDto,projectModules);

        modulesRepository.save(projectModules);
        return moduleDto;
    }


}
