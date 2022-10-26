package io.github.dingdangdog.actuator.impl;

import io.github.dingdangdog.actuator.DbActuatorInterface;
import io.github.dingdangdog.config.DbBase;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * MySQL数据初始化执行器
 *
 * @author DDD
 * @since 2022/5/13 15:38
 */
@Slf4j
public class MySqlActuator implements DbActuatorInterface {
    private static final String VERSION_5 = "5";
    private static final String DRIVER_5 = "com.mysql.jdbc.Driver";
    private static final String VERSION_8 = "8";
    private static final String DRIVER_8 = "com.mysql.cj.jdbc.Driver";

    private String name = null;
    private DataSource dataSource = null;
    private DbBase dbBase = null;

    public MySqlActuator(String name, DataSource dataSource, DbBase dbBase) {
        this.name = name;
        this.dataSource = dataSource;
        this.dbBase = dbBase;
    }

    @Override
    public void init() {
        if (dbBase.getCreate()) {
            // 创建数据库
            if (!createDataBase()) {
                log.error("--------DDD---- Datasource {} Init Error: DataBase Create Exception ----DDD--------", name);
                return;
            }
        }

        // 创建 数据库连接 和 sql执行器
        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement()) {
            // 关闭自动提交
            conn.setAutoCommit(false);
            // 执行覆盖sql脚本
            coverBySqlFile(conn, statement);
        } catch (IOException e) {
            log.error("--------DDD---- Datasource {} Init Connection Exception: {} ----DDD--------", name, e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            log.error("--------DDD---- Datasource {} Init SQL Exception: {} ----DDD--------", name, e.getMessage());
            e.printStackTrace();
        }
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

    @Override
    public void coverBySqlFile(Connection conn, Statement statement) throws IOException, SQLException {

    }

    @Override
    public List<String> getEffectiveSql(File file) throws IOException {
        return null;
    }

    @Override
    public void executeSql(List<String> sqlList, Statement statement) throws SQLException {

    }
}
