package com.port.testcloud.autotestcloud.service.cases.impl;

import com.port.testcloud.autotestcloud.domain.DbOperation;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.repository.cases.DbOperationRepository;
import com.port.testcloud.autotestcloud.service.DbConfigService;
import com.port.testcloud.autotestcloud.service.cases.DbOperationService;
import com.port.testcloud.autotestcloud.service.cases.TestCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @ClassName: DbOperationServiceImpl
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-01 18:37
 * @Description: 数据库操作
 */

@Slf4j
@Service
public class DbOperationServiceImpl implements DbOperationService {

    @Autowired
    private DbOperationRepository dbOperationRepository;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private DbConfigService dbConfigService;

    @Override
    public List<DbOperation> findByCaseId(String caseId) {
        return dbOperationRepository.findByCaseId(caseId);
    }

    @Override
    public DbOperation save(DbOperation dbOperation) {
        testCaseService.isExist(dbOperation.getCaseId());
        dbConfigService.isExist(dbOperation.getDbConfigId());
        dbOperationRepository.save(dbOperation);
        return dbOperation;
    }

    @Override
    public DbOperation isExist(String id) {
        DbOperation dbOperation;
        try {
            dbOperation = dbOperationRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            log.error("【数据库配置查询】dbOperationId 不存在：{}", id);
            throw new AutoTestException(ResultEnums.DB_OPERATION_NOT_EXIST);
        }
        return dbOperation;

    }

    @Override
    public void delete(String id) {
        isExist(id);
        dbOperationRepository.deleteById(id);
    }
}
