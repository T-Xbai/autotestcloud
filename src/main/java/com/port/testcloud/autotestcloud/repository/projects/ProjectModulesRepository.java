package com.port.testcloud.autotestcloud.repository.projects;

import com.port.testcloud.autotestcloud.domain.ProjectModules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectModulesRepository extends JpaRepository<ProjectModules, String> {



    /* 查询项目下所有的模块 */
    List<ProjectModules> findByProjectIdAndIsDelete(String projectId, Integer isDelete);


    /* 根据父级模块，所有子模块 */
    @Query("select m from ProjectModules m where  m.parentId=?1 and m.isDelete=?2 order by m.indexs asc")
    List<ProjectModules> findByParentId(String parentId, Integer isDelete);

//    @Query("select m from ProjectModules m where m.projectId=?1 and m.parentId=?2 and m.indexs=?3 and m.isDelete=?4")
    List<ProjectModules> findByProjectIdAndParentIdAndIndexsAndIsDelete(String projectId,String parentId, Integer indexs, Integer isDelete);


}
