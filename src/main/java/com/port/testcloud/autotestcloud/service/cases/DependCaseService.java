package com.port.testcloud.autotestcloud.service.cases;

import com.port.testcloud.autotestcloud.domain.DependCase;

import java.util.List;

public interface DependCaseService {

    /**
     * 根据caseId 获取
     * @param caseId
     * @return
     */
    List<DependCase> findByCaseId(String caseId);

    /**
     * 添加修改操作
     * @param dependCase
     * @return
     */
    DependCase save(DependCase dependCase);

    /**
     * 删除操作
     * @param id
     */
    void delete(String id);


    DependCase isExist(String id);

}
