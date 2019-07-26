package com.port.testcloud.autotestcloud.repository;

import com.port.testcloud.autotestcloud.domain.ProjectModules;
import com.port.testcloud.autotestcloud.dto.ModuleDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectModulesRepository extends JpaRepository<ProjectModules,String> {

    /* 查询项目下所有的模块 */
    List<ProjectModules> findByProjectIdAndIsDelete(String projectId, Integer isDelete);


    /* 根据父级模块，所有子模块 */
    List<ProjectModules> findByParentIdAndIsDelete(String parentId, Integer isDelete);

    List<ProjectModules> findByParentIdAndIndex(String parentId, Integer index);




}
