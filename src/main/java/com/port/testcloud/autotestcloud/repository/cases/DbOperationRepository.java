package com.port.testcloud.autotestcloud.repository.cases;

import com.port.testcloud.autotestcloud.domain.DbOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName: DbOperationRepository
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-01 18:32
 * @Description:
 */
public interface DbOperationRepository extends JpaRepository<DbOperation, String> {

    List<DbOperation> findByCaseId(String caseId);

    List<DbOperation> findByCaseIdAndOperation(String caseId, Integer operation);

}
