package top.oldmoon.dbinit.config;

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
     * 数据库类型
     */
    private String dbType = "mysql";
    /**
     * 是否启用
     */
    private Boolean enable = false;
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
    private String driverClassName;
    /**
     * 脚本文件夹
     */
    private String fileDir = "mysql";
}
