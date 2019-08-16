package com.port.testcloud.autotestcloud.repository.cases;

import com.port.testcloud.autotestcloud.domain.DependCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName: DependCaseRepository
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-01 14:09
 * @Description: 用例依赖
 */
public interface DependCaseRepository extends JpaRepository<DependCase,String> {

    List<DependCase> findByCaseId(String caseId);

    List<DependCase> findByCaseIdAndOperation(String caseId,Integer operation);




}
