package io.github.dingdangdog.configure;

import io.github.dingdangdog.dbinit.clear.AutoClearListener;
import io.github.dingdangdog.dbinit.runner.DbInitRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import io.github.dingdangdog.dbinit.config.DbInitConfig;

import javax.annotation.Resource;

/**
 * db-init需要注册到Spring的类统一管理
 *
 * @author DDD
 * @since 2022/5/18 16:15
 */
@EnableConfigurationProperties({DbInitConfig.class})
public class DbInitAutoConfigure {
    @Resource
    ApplicationContext context;
    @Resource
    DbInitConfig dbInitConfig;

    @Bean("autoClearListener")
    @Primary
    public AutoClearListener getAutoClear() {
        return new AutoClearListener(context);
    }

    @Bean("dbInitRunner")
    @Primary
    public DbInitRunner getDbInitRunner(@Qualifier("autoClearListener") AutoClearListener autoClearListener) {
        return new DbInitRunner(context, autoClearListener, dbInitConfig);
    }

}
