package top.oldmoon.dbinit.actuator;

import top.oldmoon.dbinit.log.entity.DbInitContext;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 数据库初始化基本接口管理
 *
 * @author DDD
 * @since 2022/5/10 16:24
 */
public interface DbActuatorInterface {
    /**
     * 初始化逻辑入口
     *
     * @author DDD
     * @since 2022/5/10 16:26
     */
    void init();

    /**
     * 覆盖执行sql脚本文件
     *
     * @param conn      数据库连接，传入是为了控制事务提交、回滚
     * @param statement sql执行器
     * @throws IOException  底层方法[getEffectiveSql()]抛出
     * @throws SQLException 数据库相关操作异常时抛出
     * @author DDD
     * @since 2022/5/11 17:19
     */
    void coverBySqlFile(Connection conn, Statement statement) throws IOException, SQLException;

    /**
     * 处理sql脚本，获取其中有效的sql
     * <p>并返回有效sql集合
     *
     * @param file sql脚本文件对象
     * @return List 有效的sql集合
     * @throws IOException sql脚本文件读取出错时抛出
     * @author DDD
     * @since 2022/5/11 17:24
     */
    List<String> getEffectiveSql(File file) throws IOException;

    /**
     * 批量执行sql：
     * <p>遍历sql集合，单独执行每一个sql</p>
     *
     * @param sqlList   sql集合
     * @param statement sql执行器
     * @throws SQLException sql语句执行出错时抛出
     * @author DDD
     * @since 2022/5/11 17:27
     */
    void executeSql(List<String> sqlList, Statement statement) throws SQLException;

    /**
     * 记录日志
     *
     * @param context 日志内容
     * @author DDD
     * @since 2022/5/18 15:27
     */
    void logging(DbInitContext context);
}
