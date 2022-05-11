package top.oldmoon.husoul.common;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ServerCache {
    // 通用缓存
    private static Map<String, Object> cacheMap;
    // token缓存
    private static Map<String, String> token;

    public Map<String, Object> getCacheMap() {
        return this.cacheMap;
    }

    public void setCache(String cacheKey, Object cacheInfo) {
        if(this.cacheMap == null){
            this.cacheMap = new HashMap<>();
        }
        this.cacheMap.put(cacheKey, cacheInfo);
    }

    public Object getCache(String cacheKey){
        if(cacheMap == null){
            return null;
        }
        return cacheMap.get(cacheKey);
    }

    public Map<String, String> getTokenMap() {
        return this.token;
    }

    public void setToken(String key, String value) {
        if(this.token == null){
            this.token = new HashMap<>();
        }
        this.token.put(key, value);
    }

    public String getToken(String key){
        if(this.token == null){
            return null;
        }
        return this.token.get(key);
    }

    public void removeToken(String userid) {
        if(this.token != null){
            this.token.remove(userid);
        }
    }
}
