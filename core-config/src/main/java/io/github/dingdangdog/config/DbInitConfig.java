package io.github.dingdangdog.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 快捷配置类
 *
 * @author DDD
 * @since 2022/5/12 14:07
 */
@Component("dbInitConfig")
@ConfigurationProperties(prefix = "db-init")
@Setter
@Getter
public class DbInitConfig {
    /**
     * 多数据源集合
     */
    private List<DbBase> dbList;

    public static final String TYPE_MYSQL = "MYSQL";
    public static final String TYPE_ORACLE = "ORACLE";
    public static List<String> supportType;

    static {
        supportType = new ArrayList<>();
        supportType.add(TYPE_MYSQL);
        supportType.add(TYPE_ORACLE);
    }
}
