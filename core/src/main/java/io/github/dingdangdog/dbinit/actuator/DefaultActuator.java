package io.github.dingdangdog.dbinit.actuator;

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
    private final String name;
    private final DataSource dataSource;

    public DefaultActuator(@NonNull String name, @NonNull DataSource dataSource) {
        this.name = name;
        this.dataSource = dataSource;
    }

    @Override
    public void init() {
        // 创建 数据库连接 和 sql执行器
        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement()) {
            // 关闭自动提交
            conn.setAutoCommit(false);
            // 执行更新类sql
            updateSql(conn, statement);
            // 执行覆盖类sql脚本
            coverBySqlFile(conn, statement);
        } catch (SQLException | IOException e) {
            log.error("数据库初始化失败：初始化出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void coverBySqlFile(Connection conn, Statement statement) throws IOException, SQLException {
        File[] files = FileUtilOm.getRootFiles(name);
        log.info("-=-=-=-=-=-=-=-=覆盖数据开始=-=-=-=-=-=-=-=-=-");
        DbInitContext context = DbInitContextManager.getContext();
        context.setFileName(name);
        String fileName = null;
        try {
            for (File file : files) {
                fileName = file.getName();
                if (fileName.endsWith(".sql")) {
                    List<String> sqlList = getEffectiveSql(file);
                    context.setSqlNumber(sqlList.size());
                    executeSql(sqlList, statement);
                }
            }
        } catch (SQLException | IOException e) {
            log.error("-=-=-=-=-=-=-=-=覆盖数据出错：{}文件执行出错！", fileName);
            conn.rollback();
            throw e;
        }
        conn.commit();
        log.info("-=-=-=-=-=-=-=-=覆盖数据结束=-=-=-=-=-=-=-=-=-");
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
                log.error("Sql执行出错：{}", sql);
                throw e;
            }
        }
    }

    @Override
    public void logging(DbInitContext context) {

    }

    private void updateSql(Connection conn, Statement statement) {
    }
}
