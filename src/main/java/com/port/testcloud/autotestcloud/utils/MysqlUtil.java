package com.port.testcloud.autotestcloud.utils;

import com.port.testcloud.autotestcloud.domain.DbConfig;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName: MysqlUtil
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-14 15:37
 * @Description:
 */
@Slf4j
public class MysqlUtil {

    private final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    private String db_URL;

    private String user;

    private String passwd;


    public MysqlUtil(DbConfig dbConfig) {
        this.db_URL = "jdbc:mysql://" + dbConfig.getHost() + ":" + dbConfig.getPort() + "/" +
                dbConfig.getDatabaseName() + "?useSSL=false&serverTimezone=UTC";
        this.user = dbConfig.getUsername();
        this.passwd = dbConfig.getPassword();
        log.warn("数据库连接 URL：{}",db_URL);
        log.warn("数据库连接 User：{}",user);
        log.warn("数据库连接 Passwd：{}",passwd);


    }

    public Boolean execute(String sql) {
        Connection conn = null;
        Statement statement = null;
        boolean execute ;
        try {
            Class.forName(this.DB_DRIVER);
            conn = DriverManager.getConnection(this.db_URL, this.user, this.passwd);
            statement = conn.createStatement();
            execute = statement.execute(sql);

        } catch (ClassNotFoundException e) {
            log.error("db_driver 异常：", e);
            execute = false;
        } catch (SQLException e) {
            log.error("数据库执行异常：", e);
            execute = false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error("", e);
                    execute = false;
                }
            }

            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error("", e);
                    execute = false;
                }
            }
        }

        return execute;


    }
}
