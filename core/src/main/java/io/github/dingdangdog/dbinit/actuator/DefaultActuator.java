package io.github.dingdangdog.dbinit.actuator;

import io.github.dingdangdog.dbinit.config.DbBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import io.github.dingdangdog.dbinit.log.entity.DbInitContext;
import io.github.dingdangdog.dbinit.log.manager.DbInitContextManager;
import io.github.dingdangdog.file.FileUtilOm;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库初始化配置
 *
 * @author DDD
 * @since `2022/5/9` 13:33
 */
@Slf4j
public class DefaultActuator implements DbActuatorInterface {
    protected final String name;
    protected final DataSource dataSource;
    protected final DbBase dbBase;

    public DefaultActuator(@NonNull String name, @NonNull DataSource dataSource, @NonNull DbBase dbBase) {
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
        return false;
    }

    @Override
    public void coverBySqlFile(Connection conn, Statement statement) throws IOException, SQLException {
        File[] files = FileUtilOm.getRootFiles(name);
        log.info("--------DDD---- Datasource {} Cover Begin ----DDD--------", name);
        DbInitContext context = DbInitContextManager.getContext();
        context.setFileName(name);
        String fileName = null;
        try {
            for (File file : files) {
                fileName = file.getName();
                if (fileName.endsWith(".sql")) {
                    List<String> sqlList = getEffectiveSql(file);
                    context.setSqlNum(sqlList.size());
                    executeSql(sqlList, statement);
                }
            }
        } catch (SQLException | IOException e) {
            log.error("--------DDD---- Datasource {} Cover Error: File {}, Message: {} ----DDD--------", name, fileName, e.getMessage());
            conn.rollback();
            throw e;
        }
        conn.commit();
        log.info("--------DDD---- Datasource {} Cover Success ----DDD--------", name);
    }

    @Override
    public List<String> getEffectiveSql(File file) throws IOException {
        List<String> sqlList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean inNotes = false;
            StringBuilder sql = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("/*")) {
                    inNotes = true;
                }
                if (inNotes || line.startsWith("-- ")) {
                    if (line.contains("*/")) {
                        inNotes = false;
                    }
                    continue;
                }
                sql.append(line);
                sql.append(" ");
                if (line.contains(";")) {
                    sqlList.add(sql.toString());
                    sql.setLength(0);
                }
            }
        }
        return sqlList;
    }

    @Override
    public void executeSql(List<String> sqlList, Statement statement) throws SQLException {
        for (String sql : sqlList) {
            try {
                statement.execute(sql);
            } catch (SQLException e) {
                log.error("--------DDD---- Datasource {} SQL Execute Error: Sql: {} Exception: {} ----DDD--------", name, sql, e.getMessage());
                throw e;
            }
        }
    }
}
