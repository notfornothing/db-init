package io.github.dingdangdog.dbinit.actuator;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Oracle数据初始化执行器
 *
 * @author DDD
 * @since 2022/5/13 15:38
 */
@Slf4j
public class OracleActuator extends DefaultActuator {

    public OracleActuator(String name, DataSource dataSource) {
        super(name, dataSource);
    }

    @Override
    public void executeSql(List<String> sqlList, Statement statement) throws SQLException {
        for (String sql : sqlList) {
            try {
                // oracle执行sql需要去除分号(;)
                sql = sql.replace(";", "");
                statement.execute(sql);
            } catch (SQLException e) {
                // oracle无表时执行删表语句会报错，手动捕获判定为正常，并继续运行
                if (sql.toUpperCase().contains("DROP") && e.getMessage().contains("ORA-00942")) {
                    continue;
                }
                log.error("--------DDD---- Datasource {} SQL Execute Error: Sql: {} Exception: {} ----DDD--------", super.name, sql, e.getMessage());
                throw e;
            }
        }
    }
}
