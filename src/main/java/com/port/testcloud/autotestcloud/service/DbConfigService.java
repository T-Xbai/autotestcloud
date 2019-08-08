package com.port.testcloud.autotestcloud.service;

import com.port.testcloud.autotestcloud.domain.DbConfig;

import java.util.List;

/**
 * @ClassName: DbConfigService
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-02 09:41
 * @Description: 数据库配置
 */

public interface DbConfigService {


    DbConfig findOne(String id);

    List<DbConfig> findAll();

    DbConfig save(DbConfig dbConfig);

    void isExist(String id);


}
