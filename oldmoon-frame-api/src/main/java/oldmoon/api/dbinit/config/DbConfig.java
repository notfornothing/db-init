package oldmoon.api.dbinit.config;

import lombok.Data;

/**
 * 数据库基本信息配置
 *
 * @author hupg
 * @date 2022/5/10 16:27
 */
@Data
public class DbConfig {
    /**
     * 数据库IP
     */
    private String ip;
    /**
     * 数据库端口
     */
    private String port;
    /**
     * 数据库-名称
     */
    private String database;
    /**
     * 数据库-链接地址
     */
    private String url;
    /**
     * 数据库账号
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * 数据库连接类全类名
     */
    private String className;
}
