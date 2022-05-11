package oldmoon.api.dbinit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Mysql 数据库配置类
 *
 * @author hupg
 * @date 2022/5/10 16:31
 */
@ConfigurationProperties(prefix = "dbinit.mysql")
public class MySqlConfig extends DbConfig {
    private String fileDir = "mysql";

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }
}
