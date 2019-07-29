package com.port.testcloud.autotestcloud.repository;

import com.port.testcloud.autotestcloud.domain.TestCase;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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
     * 根据删除状态，查询模块下所有的 case
     * @param moduleId
     * @param isDelete
     * @return
     */
    Page<TestCase> findByModuleIdAndIsDelete(String moduleId,  Integer isDelete);


    /**
     * 查询模块下的 case 是否存在对应的 indexs
     * @param moduleId
     * @param indexs
     * @param isDelete
     * @return
     */
    TestCase findByModuleIdAndIndexsAndIsDelete(String moduleId, String indexs , Integer isDelete);


    /**
     * 根据用例名称查询
     * @param caseName
     * @return
     */
    List<TestCase> findByCaseNameContainingAndIsDelete(String caseName,Integer isDelete);

    




}
