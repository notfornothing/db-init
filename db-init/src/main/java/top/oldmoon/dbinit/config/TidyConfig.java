package top.oldmoon.dbinit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "dbinit")
public class TidyConfig {
    private Map<String, Boolean> dbs = new HashMap<>();

    public Map<String, Boolean> getDbs() {
        return dbs;
    }

    public void setDbs(Map<String, Boolean> dbs) {
        this.dbs = dbs;
    }
}
