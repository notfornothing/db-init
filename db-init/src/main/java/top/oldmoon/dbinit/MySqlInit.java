package top.oldmoon.dbinit;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import reactor.util.annotation.NonNull;
import top.oldmoon.dbinit.config.MySqlConfig;
import top.oldmoon.file.FileUtilOm;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据库初始化配置（MySQL）
 *
 * @author hupg
 * @date `2022/5/9` 13:33
 */
@Slf4j
public class MySqlInit implements InitInterface {
    private final MySqlConfig config;

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
            coverBySqlFile(conn, statement);
        } catch (SQLException | IOException e) {
            log.error("数据库{}初始化失败：初始化出错：{}", url, e.getMessage());
            e.printStackTrace();
        }
    }

    private void coverBySqlFile(Connection conn, Statement statement) throws SQLException, IOException {
        File[] files = FileUtilOm.getRootFiles(config.getFileDir());
        log.info("-=-=-=-=-=-=-=-=覆盖数据开始=-=-=-=-=-=-=-=-=-");
        for (File file : files) {
            String name = file.getName();
            if (name.endsWith(".sql")) {
                try {
                    List<String> sqlList = formatEffectiveSql(file);
                    executeSql(sqlList, statement);
                } catch (SQLException | IOException e) {
                    log.error("{}执行出错！", name);
                    conn.rollback();
                    throw e;
                }
            }
        }
        conn.commit();
        log.info("-=-=-=-=-=-=-=-=覆盖数据结束=-=-=-=-=-=-=-=-=-");
    }


    private List<String> formatEffectiveSql(File file) throws IOException {
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
                if (line.contains(";")) {
                    sqlList.add(sql.toString());
                    sql.setLength(0);
                }
            }
        }
        return sqlList;
    }


    private void coverSql(Connection conn, Statement statement) throws SQLException {
        File[] files = FileUtilOm.getRootFiles(config.getFileDir());
        log.info("-=-=-=-=-=-=-=-=覆盖数据开始=-=-=-=-=-=-=-=-");
        for (File file : files) {
            String name = file.getName();
            if (name.endsWith(".sql")) {
                String fileInfo = FileUtilOm.getFileInfo(file);
                try {
                    String[] split = fileInfo.split(";");
                    executeSql(Arrays.asList(split), statement);
                } catch (SQLException e) {
                    log.error("{}执行出错！", name);
                    conn.rollback();
                    throw e;
                }
            }
        }
        conn.commit();
        log.info("-=-=-=-=-=-=-=-=覆盖数据结束=-=-=-=-=-=-=-=-");
    }

    private void executeSql(List<String> sqlList, Statement statement) throws SQLException {
        for (String sql : sqlList) {
            if (!StringUtil.isNullOrEmpty(sql.trim())) {
                statement.execute(sql);
            }
        }
    }

    private void updateSql(Connection conn, Statement statement) {
    }

    @Override
    public void close() {

    }
}
