package com.port.testcloud.autotestcloud.service.impl;

import com.port.testcloud.autotestcloud.domain.ProjectModules;
import com.port.testcloud.autotestcloud.domain.Projects;
import com.port.testcloud.autotestcloud.dto.ModuleDto;
import com.port.testcloud.autotestcloud.enums.DeleteStatusEnums;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.repository.ProjectModulesRepository;
import com.port.testcloud.autotestcloud.service.ProjectModuleService;
import com.port.testcloud.autotestcloud.service.ProjectsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.port.testcloud.autotestcloud.convert.ProjectModuleToModuleDto.convert;
import static com.port.testcloud.autotestcloud.enums.DeleteStatusEnums.NORMAL;

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


    /**
     * 查询单个模块信息
     *
     * @param id
     * @return
     */
    @Override
    public ModuleDto findOne(String id) {
        ProjectModules projectModules = modulesRepository.findById(id).orElse(new ProjectModules());
        return convert(projectModules);
    }

    /**
     * 查询单个模块下所有子集
     *
     * @param moduleId
     * @return
     */
    @Override
    public List<ModuleDto> sonAll(String moduleId) {

        List<ModuleDto> byParentIdAndIsDelete = findByParentIdAndIsDelete(moduleId, NORMAL.getCode());
        byParentIdAndIsDelete.forEach(module -> {
            List<ModuleDto> sonModules = sonAll(module.getId());
            module.setSonList(sonModules);
        });
        byParentIdAndIsDelete.sort(Comparator.comparing(ModuleDto::getIndexs));
        return byParentIdAndIsDelete;
    }

    /**
     * 获取一级模块信息
     *
     * @return
     */
    @Override
    public List<ModuleDto> findFirstAll() {
        return findByParentIdAndIsDelete(null, NORMAL.getCode());
    }

    /**
     * 获取某个项目下所有模块信息
     *
     * @param projectId
     * @return
     */
    @Override
    public List<ModuleDto> findAllByProjectId(String projectId) {
        List<ProjectModules> projectModulesList = modulesRepository.findByProjectIdAndIsDelete(projectId, NORMAL.getCode());

        List<ProjectModules> modulesList = projectModulesList.stream().filter(
                module -> (StringUtils.isEmpty(module.getParentId()))
        ).collect(Collectors.toList());

        List<ModuleDto> moduleDtoList = convert(modulesList);
        if (projectModulesList.size() > 0) {
            moduleDtoList.forEach(module -> {
                List<ModuleDto> sonAll = sonAll(module.getId());
                module.setSonList(sonAll);
            });
        }
        return moduleDtoList;
    }


    @Override
    public List<ModuleDto> findByParentIdAndIsDelete(String parentId, Integer isDelete) {

        List<ProjectModules> byParentIdAndIsDelete = modulesRepository.findByParentId(parentId, isDelete);
        return convert(byParentIdAndIsDelete);
    }




    /**
     * 创建或者更新模块
     *
     * @param moduleDto
     * @return
     */
    @Override
    @Transactional
    public ModuleDto save(ModuleDto moduleDto) {

        // 父级为 null  or 传入的父级模块必须存在
        if (!StringUtils.isEmpty(moduleDto.getParentId())) {
            ModuleDto parentModule = findOne(moduleDto.getParentId());
            if (parentModule == null || parentModule.getId() == null) {
                log.error("【模块操作】父级模块不存在,parentId = {} , queryResult = {}", moduleDto.getParentId(), parentModule);
                throw new AutoTestException(ResultEnums.PARENT_MODULE_NOT_EXIST);
            }

            // 有父级模块的时候，必须校验 projectId 相等
            if (!parentModule.getProjectId().equals(moduleDto.getProjectId())){
                log.error("【模块操作】项目Id不一致: {} != {}", parentModule.getProjectId(),moduleDto.getProjectId());
                throw new AutoTestException(ResultEnums.PROJECT_ID_NOT_EQUALITY);
            }

        }


        //校验 project 是否存在
        Projects queryProject = projectsService.findOne(moduleDto.getProjectId());
        if (queryProject == null || (queryProject.getIsDelete().compareTo(DeleteStatusEnums.DEL.getCode()) == 0)) {
            log.error("【模块操作】projectId 异常：projectId = {}", moduleDto.getProjectId());
            throw new AutoTestException(ResultEnums.PROJECT_ERROR);
        }

        List<ProjectModules> byParentIdAndIndex = modulesRepository.findByProjectIdAndParentIdAndIndexsAndIsDelete(
                moduleDto.getProjectId(),moduleDto.getParentId(), moduleDto.getIndexs(), NORMAL.getCode());

        if (byParentIdAndIndex != null && byParentIdAndIndex.size() > 0) {
            log.error("【模块操作】同一个父模块下，排序不可重复：parentId = {} , index = {}",
                    moduleDto.getParentId(), moduleDto.getIndexs());
            throw new AutoTestException(ResultEnums.MODULE_INDEX_NOT_REPETITION);
        }

        ProjectModules projectModules = new ProjectModules();
        BeanUtils.copyProperties(moduleDto, projectModules);

        modulesRepository.save(projectModules);
        return moduleDto;
    }


}
