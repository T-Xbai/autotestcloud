package com.port.testcloud.autotestcloud.service.impl;

import com.port.testcloud.autotestcloud.domain.DbConfig;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.repository.DbConfigRepository;
import com.port.testcloud.autotestcloud.service.DbConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @ClassName: DbConfigServiceImpl
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-02 09:43
 * @Description: 数据库配置
 */

@Service
@Slf4j
public class DbConfigServiceImpl implements DbConfigService {


    @Autowired
    private DbConfigRepository configRepository;

    @Override
    public DbConfig findOne(String id) {
        return  configRepository.findById(id).orElse(new DbConfig());
    }

    @Override
    public List<DbConfig> findAll() {
        return configRepository.findAll();
    }

    @Override
    public DbConfig save(DbConfig dbConfig) {
        return configRepository.save(dbConfig);
    }

    @Override
    public void isExist(String id) {
        try {
            configRepository.findById(id).get();
        }catch (NoSuchElementException e){
            log.error("【数据库配置】查询ID不存在：{}",id);
            throw new AutoTestException(ResultEnums.DB_CONFIG_IS_EXIST);
        }
    }
}
