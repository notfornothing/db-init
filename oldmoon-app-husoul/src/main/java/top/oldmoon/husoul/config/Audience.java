package top.oldmoon.husoul.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description token配置信息
 * @Author hupg
 * @Date 2021/5/14 10:47
 */
@ConfigurationProperties(prefix = "audience")
@Component
public class Audience {
    private String serverId;
    private String publicKey;
    private int dueTime;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public int getDueTime() {
        return dueTime;
    }

    public void setDueTime(int dueTime) {
        this.dueTime = dueTime;
    }
}
