package io.github.dingdangdog.dbinit.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据源基本配置
 *
 * @author DDD
 * @since 2022/5/18 15:42
 */
@Setter
@Getter
@ToString
public class DbBase {
    /**
     * 是否初始化：默认为否
     */
    private Boolean enable = false;
    /**
     * 数据源名称，应为Spring的DataSource实例名：if {@link #enable} = true，必填
     */
    private String name;
    /**
     * 数据库类型，Mysql、Oracle：if {@link #enable} = true，必填
     */
    private String type;
    /**
     * 数据库版本：Mysql：5、6、8
     */
    private String version = "default";

    /**
     * 是否创建数据库：默认为否
     */
    private Boolean create = false;
    /**
     * 数据库地址：if {@link #create} = true，必填
     */
    private String url;
    /**
     * 数据库名：if {@link #create} = true，必填
     */
    private String baseName;
    /**
     * 数据库用户名：if {@link #create} = true，必填
     */
    private String username;
    /**
     * 数据库用户密码：if {@link #create} = true，必填
     */
    private String password;
}
