package com.port.testcloud.autotestcloud.repository.cases;

import com.port.testcloud.autotestcloud.domain.TestCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, String> {

    /**
     * 查询所有未删除的 case
     *
     * @param isDelete
     * @param pageable
     * @return
     */
    Page<TestCase> findAllByIsDelete(Integer isDelete, Pageable pageable);

    /**
     * 根据删除状态，查询模块下所有的 case - 分页查询
     *
     * @param moduleId
     * @param isDelete
     * @param pageable
     * @return
     */
    Page<TestCase> findByModuleIdAndIsDelete(String moduleId, Integer isDelete, Pageable pageable);


    /**
     * 根据删除状态，查询模块下所有的 case
     *
     * @param moduleId
     * @param isDelete
     * @return
     */
    @Query("select t.id from TestCase t where t.moduleId=?1 and t.isDelete=?2 order by t.indexs asc")
    List<String> findByModuleToAllCaseId(String moduleId, Integer isDelete);

    /**
     * 查询模块下的 case 是否存在对应的 indexs
     *
     * @param moduleId
     * @param indexs
     * @param isDelete
     * @return
     */
    @Query("select t from TestCase t where t.moduleId=?1 and t.indexs=?2 and t.isDelete=?3")
    TestCase findByModuleIdAndIndex(String moduleId, Integer indexs, Integer isDelete);


    /**
     * 根据用例名称查询
     *
     * @param caseName
     * @return
     */
    @Query("select t from TestCase t where t.caseName like concat('%',?1,'%') and t.isDelete=?2")
    List<TestCase> findByCaseName(String caseName, Integer isDelete);


    /**
     * 根据用例名称查询 （模块为单位）
     *
     * @param caseName
     * @return
     */
    @Query("select t from TestCase t where t.moduleId=?1 and t.caseName like concat('%',?2,'%') and t.isDelete=?3 ")
    List<TestCase> findByCaseName(String moduleId, String caseName, Integer isDelete);



}
