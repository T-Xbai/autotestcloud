package com.port.testcloud.autotestcloud.service.cases;

import com.port.testcloud.autotestcloud.domain.DbOperation;

import java.util.List;

public interface DbOperationService {

    List<DbOperation> findByCaseId(String caseId);

    DbOperation save(DbOperation dbOperation);

    DbOperation isExist(String id);

    void delete(String id);






}
