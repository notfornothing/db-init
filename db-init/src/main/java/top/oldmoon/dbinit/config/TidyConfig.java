package top.oldmoon.dbinit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hupg
 * @date 2022/5/12 14:07
 */
@ConfigurationProperties(prefix = "dbinit")
public class TidyConfig {
    private Map<String, Boolean> dbs = new HashMap<>();

    public static final String TYPE_MYSQL = "MYSQL";
    public static final String TYPE_ORACLE = "ORACLE";

    public static List<String> supportType;

    static {
        supportType = new ArrayList<>();
        supportType.add(TYPE_MYSQL);
        supportType.add(TYPE_ORACLE);
    }

    private Map<String, String> dbType = new HashMap<>();

    public Map<String, Boolean> getDbs() {
        return dbs;
    }

    public void setDbs(Map<String, Boolean> dbs) {
        this.dbs = dbs;
    }

    public Map<String, String> getDbType() {
        return dbType;
    }

    public void setDbType(Map<String, String> dbType) {
        this.dbType = dbType;
    }
}
