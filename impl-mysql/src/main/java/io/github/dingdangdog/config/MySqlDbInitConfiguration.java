package io.github.dingdangdog.config;

import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;

/**
 * mysql数据源初始化配置
 *
 * @author DingDangDog
 * @date 2022/11/21
 */
@Setter
@Getter
public class MySqlDbInitConfiguration {

    public MySqlDbInitConfiguration() {
    }

    /**
     * 是否初始化：默认为true--启用初始化
     */
    private Boolean enable = true;

    private Integer initType = 0;
    private DataSource dataSource;

    public MySqlDbInitConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
        this.initType = 1;
    }

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

    public MySqlDbInitConfiguration(String url, String baseName, String username, String password) {
        this.initType = 2;
        this.url = url;
        this.baseName = baseName;
        this.username = username;
        this.password = password;
    }


}
