package top.oldmoon.dbinit.actuator;

import javax.sql.DataSource;

/**
 * MySQL数据初始化执行器
 *
 * @author hupg
 * @date 2022/5/13 15:38
 */
public class MySqlActuator extends DefaultActuator {
    public MySqlActuator(String name, DataSource dataSource) {
        super(name, dataSource);
    }
}