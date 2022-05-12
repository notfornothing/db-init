package top.oldmoon.dbinit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Oracle 数据库配置类
 *
 * @author hupg
 * @date 2022/5/10 16:32
 */
@ConfigurationProperties(prefix = "dbinit.oracle")
public class OracleConfig extends DbConfig {
}
