package com.port.testcloud.autotestcloud.service.cases;

import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName: TestCaseService
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-29 23:40
 * @Description: 用例
 */
public interface TestCaseService {

    /**
     * 查询单个用例详情信息
     * @param caseId
     * @return
     */
    TestCaseDto findOne(String caseId);

    /**
     * 查询某个模块下所有用例的 -> 分页查询
     * @param moduleId
     * @param pageable
     * @return
     */
    Page<TestCaseDto> findAll(String moduleId, Pageable pageable);

    /**
     * 查询所有用例 -> 分页查询
     * @param pageable
     * @return
     */
    Page<TestCaseDto> findAll(Pageable pageable);

    /**
     * 查询某个模块下，对应 index 值得用例
     * @param moduleId
     * @param indexs
     * @return
     */
    TestCaseDto findByIndex(String moduleId,Integer indexs);

    /**
     * 根据用例名称搜索 -> 模糊搜索
     * @param caseName
     * @return
     */
    List<TestCaseDto> findByCaseName(String caseName);

    /**
     * 以模块为单位，搜索用例 -> 模糊搜索
     * @param moduleId
     * @param caseName
     * @return
     */
    List<TestCaseDto> findByCaseName(String moduleId ,String caseName);

    TestCaseDto save(TestCaseDto testCaseDto);

    /**
     * del
     * @param caseId
     * @return
     */
    void delete(String caseId);

    TestCaseDto isExist(String caseId);

}
