package com.port.testcloud.autotestcloud.repository;

import com.port.testcloud.autotestcloud.domain.DbConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: DbConfigRepository
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-02 09:45
 * @Description: 数据库配置
 */
public interface DbConfigRepository extends JpaRepository<DbConfig,String> {
    
    

}
