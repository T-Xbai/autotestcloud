package com.port.testcloud.autotestcloud.service.cases;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.port.testcloud.autotestcloud.domain.DbOperation;
import com.port.testcloud.autotestcloud.domain.RunResult;

import java.util.List;

public interface DbOperationService {

    DbOperation findOne(String id);

    List<DbOperation> findByCaseId(String caseId);

    DbOperation save(DbOperation dbOperation);

    DbOperation isExist(String id);

    void delete(String id);

    void beforeExecute(String caseId);

    void afterExecute(RunResult runResult, String responseBody, String caseId);

    Boolean execute(DbOperation dbOperation);


}
