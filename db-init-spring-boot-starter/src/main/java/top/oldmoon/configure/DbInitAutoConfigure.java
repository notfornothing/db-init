package top.oldmoon.configure;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import top.oldmoon.dbinit.clear.AutoClear;
import top.oldmoon.dbinit.config.DbInitConfig;
import top.oldmoon.dbinit.runner.DbInitRunner;

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

    @Bean("autoClear")
    @Primary
    public AutoClear getAutoClear() {
        return new AutoClear(context);
    }

    @Bean("dbInitRunner")
    @Primary
    public DbInitRunner getDbInitRunner(@Qualifier("autoClear") AutoClear autoClear) {
        return new DbInitRunner(context, autoClear, dbInitConfig);
    }

}
