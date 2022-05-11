package oldmoon.api.dbinit;

import lombok.extern.slf4j.Slf4j;
import oldmoon.api.dbinit.config.MySqlConfig;
import oldmoon.api.file.FileUtilOm;
import reactor.util.annotation.NonNull;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库初始化配置（MySQL）
 *
 * @author hupg
 * @date `2022/5/9` 13:33
 */
@Slf4j
public class MySqlInit implements InitInterface {
    private final MySqlConfig config;

    private Statement statement;

    public MySqlInit(@NonNull MySqlConfig config) {
        this.config = config;
    }


    @Override
    public void init() {
        String url = config.getUrl();
        String className = config.getClassName();
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("数据库{}初始化失败：无法加载数据库驱动：{}", url, className);
            e.printStackTrace();
        }
        String username = config.getUsername();
        String password = config.getPassword();
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            updateSql(conn, statement);
            coverSql(conn, statement);
        } catch (SQLException e) {
            log.error("数据库{}初始化失败：初始化出错：{}", url, e.getErrorCode());
            e.printStackTrace();
        }
    }

    private void coverSql(Connection conn, Statement statement) throws SQLException {
        File[] files = FileUtilOm.getRootFiles(config.getFileDir());
        for (File file : files) {
            String name = file.getName();
            if (name.endsWith(".sql")) {
                String fileInfo = FileUtilOm.getFileInfo(file);
                try {
                    statement.execute(fileInfo);
                } catch (SQLException e) {
                    log.error("{}执行出错！", name);
                    conn.rollback();
                    throw e;
                }
            }
        }
        conn.commit();
    }

    private void updateSql(Connection conn, Statement statement) {
    }

    @Override
    public void close() {

    }
}
