package oldmoon.api.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 *
 * @author hupg
 * @date 2022/5/9 11:58
 */
@Component
public class RedisUtilOm {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtilOm.class);
    @Autowired
    private RedisTemplate redisTemplate;

    private static RedisTemplate template;

    @PostConstruct
    private void init() {
        template = redisTemplate;
    }

    /**
     * 批量删除key对应的value
     *
     * @param keys keys
     */
    public static void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern 正则
     */
    public static void removePattern(final String pattern) {
        Set<Serializable> keys = template.keys(pattern);
        if (keys.size() > 0) {
            template.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key key
     */
    public static void remove(final String key) {
        if (exists(key)) {
            template.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key key
     */
    public static boolean exists(final String key) {
        return template.hasKey(key);
    }

    /**
     * redis查询
     *
     * @param key key
     */
    public static Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = template.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * redis存储
     *
     * @param key   key
     * @param value value
     */
    public static boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = template.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * redis存储——加过期时间
     *
     * @param key        key
     * @param value      值
     * @param expireTime 过期时间
     */
    public static boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = template.opsForValue();
            operations.set(key, value);
            template.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

}
