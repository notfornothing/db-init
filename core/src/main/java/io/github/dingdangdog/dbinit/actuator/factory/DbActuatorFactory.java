package io.github.dingdangdog.dbinit.actuator.factory;

import io.github.dingdangdog.dbinit.actuator.DefaultActuator;
import io.github.dingdangdog.dbinit.actuator.MySqlActuator;
import io.github.dingdangdog.dbinit.actuator.OracleActuator;
import org.springframework.lang.NonNull;
import io.github.dingdangdog.dbinit.config.DbInitConfig;

import javax.sql.DataSource;

/**
 * 数据执行器工厂
 *
 * @author DDD
 * @since 2022/5/13 14:43
 */
public class DbActuatorFactory {

    /**
     * 创建执行器
     *
     * @param name       数据源名称
     * @param dataSource 数据源
     * @param dbType     数据源类型
     * @return top.oldmoon.dbinit.actuator.DefaultActuator
     * @author DDD
     * @since 2022/5/13 15:02
     */
    public DefaultActuator createActuator(@NonNull String name, @NonNull DataSource dataSource,
                                          @NonNull String dbType) {
        DefaultActuator actuator = null;
        if (DbInitConfig.TYPE_MYSQL.equalsIgnoreCase(dbType)) {
            actuator = new MySqlActuator(name, dataSource);
        } else if (DbInitConfig.TYPE_ORACLE.equalsIgnoreCase(dbType)) {
            actuator = new OracleActuator(name, dataSource);
        }
        return actuator;
    }

}
