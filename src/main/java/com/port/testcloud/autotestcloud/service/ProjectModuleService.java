package com.port.testcloud.autotestcloud.service;

import com.port.testcloud.autotestcloud.dto.ModuleDto;

import java.util.List;

/**
 * @ClassName: ProjectModuleService
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-25 18:19
 * @Description: 项目模块
 */
public interface ProjectModuleService {

    /* 查询模块 */
    ModuleDto findOne(String id);

    /* 查询项目下的所有模块 */
    List<ModuleDto> findByProjectIdAndIsDelete(String projectId, Integer isDelete);

    /* 查询某个模块下的所有子模块 */
    List<ModuleDto> findByParentIdAndIsDelete(String projectId, Integer isDelete);

    ModuleDto save(ModuleDto moduleDto);




}
