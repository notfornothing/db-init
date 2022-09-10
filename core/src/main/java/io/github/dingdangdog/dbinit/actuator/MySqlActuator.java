package io.github.dingdangdog.dbinit.actuator;

import io.github.dingdangdog.dbinit.config.DbBase;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * MySQL数据初始化执行器
 *
 * @author DDD
 * @since 2022/5/13 15:38
 */
@Slf4j
public class MySqlActuator extends DefaultActuator {
    private final static String VERSION_5 = "5";
    private final static String DRIVER_5 = "com.mysql.jdbc.Driver";
    private final static String VERSION_8 = "8";
    private final static String DRIVER_8 = "com.mysql.cj.jdbc.Driver";

    public MySqlActuator(String name, DataSource dataSource, DbBase dbBase) {
        super(name, dataSource, dbBase);
    }

    @Override
    public boolean createDataBase() {
        String driver = null;
        try {
            if (VERSION_5.equals(dbBase.getVersion())) {
                driver = DRIVER_5;
                Class.forName(DRIVER_5);
            } else {
                driver = DRIVER_8;
                Class.forName(DRIVER_8);
            }
        } catch (ClassNotFoundException e) {
            log.error("--------DDD---- Datasource {} Driver Class {} Not Found, Exception: {} ----DDD--------", name, driver, e.getMessage());
            e.printStackTrace();
            return false;
        }

        try (Connection conn = DriverManager.getConnection(dbBase.getUrl(), dbBase.getUsername(), dbBase.getPassword());
             Statement stat = conn.createStatement()) {
            String sql = "CREATE DATABASE {baseName}";
            sql = sql.replace("{baseName}", dbBase.getBaseName());
            // 创建数据库
            stat.executeUpdate(sql);
        } catch (SQLException e) {
            log.error("--------DDD---- Datasource {} Create DataBase {} Exception: {} ----DDD--------", name, dbBase.getBaseName(), e.getMessage());
            e.printStackTrace();
            return false;
        }
        log.error("--------DDD---- Datasource {} Create DataBase {} Success! ----DDD--------", name, dbBase.getBaseName());
        return true;
    }
}
