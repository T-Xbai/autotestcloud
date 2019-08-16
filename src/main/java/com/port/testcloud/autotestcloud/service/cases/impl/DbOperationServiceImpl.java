package com.port.testcloud.autotestcloud.service.cases.impl;

import com.jayway.jsonpath.JsonPath;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.port.testcloud.autotestcloud.domain.DbConfig;
import com.port.testcloud.autotestcloud.domain.DbOperation;
import com.port.testcloud.autotestcloud.domain.RunResult;
import com.port.testcloud.autotestcloud.dto.DataVariableDto;
import com.port.testcloud.autotestcloud.enums.DbTypeEnums;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.enums.RunOrderEnum;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.repository.cases.DbOperationRepository;
import com.port.testcloud.autotestcloud.service.DataVariableService;
import com.port.testcloud.autotestcloud.service.DbConfigService;
import com.port.testcloud.autotestcloud.service.RunResultService;
import com.port.testcloud.autotestcloud.service.cases.DbOperationService;
import com.port.testcloud.autotestcloud.service.cases.TestCaseService;
import com.port.testcloud.autotestcloud.utils.JsonUtil;
import com.port.testcloud.autotestcloud.utils.MysqlUtil;
import com.port.testcloud.autotestcloud.utils.ReplaceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    private DataVariableService dataVariableService;

    @Autowired
    private RunResultService runResultService;

    @Override
    public DbOperation findOne(String id) {
        return dbOperationRepository.findById(id).orElse(new DbOperation());
    }

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

    @Override
    public void beforeExecute(String caseId) {
        List<DbOperation> beforeOperationList = dbOperationRepository
                .findByCaseIdAndOperation(caseId, RunOrderEnum.BEFORE.getCode());
        beforeOperationList.forEach(beforeOperation -> {
            Boolean result = this.execute(beforeOperation);
            log.warn("Before execute sql: {}", beforeOperation.getRunSql());
            log.warn("Before Execute result: {}", result);
        });
    }

    @Override
    public void afterExecute(RunResult runResult, String responseBody, String caseId) {
        List<DbOperation> afterOperationList = dbOperationRepository
                .findByCaseIdAndOperation(caseId, RunOrderEnum.AFTER.getCode());


        afterOperationList.forEach(afterOperation -> {

            String sql = afterOperation.getRunSql();

            List<DataVariableDto> dataVariableDtoList = dataVariableService.get(afterOperation.getDependVariable());
            dataVariableDtoList.forEach(dataVariableDto -> {

                String expression = dataVariableDto.getValue();
                String variableValue = "\\{\\{ ".concat(dataVariableDto.getKey()).concat(" \\}\\}");
                if (expression.substring(0, 2).equals("$.")) {
                    try {
                        Object value = JsonPath.parse(responseBody).read(expression);

                        variableValue = String.valueOf(value);
                        dataVariableDto.setValue(variableValue);
                    } catch (Exception e) {
                        final String errorMes = "【执行SQL -> Json path 匹配 】匹配格式错误:";
                        runResultService.jointExceptionMsg(runResult, errorMes, e);
                        log.error(errorMes.concat("{}"), e.toString());
                    }
                    dataVariableDto.setValue(String.valueOf(variableValue));

                } else {
                    Pattern pattern = Pattern.compile(expression);
                    Matcher matcher = pattern.matcher(runResult.getResponseBody());
                    if (matcher.find()) {
                        variableValue = matcher.group(1);
                    }
                    dataVariableDto.setValue(variableValue);
                }
            });


            sql = ReplaceUtil.all(dataVariableDtoList, sql);

            afterOperation.setRunSql(sql);
            Boolean result = this.execute(afterOperation);
            log.warn("After execute sql: {}", afterOperation.getRunSql());
            log.warn("After execute result: {}", result);

        });
    }

    @Override
    public Boolean execute(DbOperation dbOperation) {
        boolean executeResult = true;
        DbConfig dbConfig = dbConfigService.findOne(dbOperation.getDbConfigId());
        if (dbConfig.getDbType().equals(DbTypeEnums.MYSQL.getCode())) {
            MysqlUtil mysqlUtil = new MysqlUtil(dbConfig);
            executeResult = mysqlUtil.execute(dbOperation.getRunSql());
        } else {

        }
        return executeResult;
    }
}
