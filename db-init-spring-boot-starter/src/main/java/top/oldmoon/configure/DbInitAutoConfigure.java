package top.oldmoon.configure;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import top.oldmoon.dbinit.clear.AutoClear;
import top.oldmoon.dbinit.config.TidyConfig;
import top.oldmoon.dbinit.runner.DbInitRunner;

import javax.annotation.Resource;

@EnableConfigurationProperties({TidyConfig.class})
public class DbInitAutoConfigure {
    @Resource
    ApplicationContext context;
    @Resource
    TidyConfig tidyConfig;

    @Bean("autoClear")
    @Primary
    public AutoClear getAutoClear() {
        return new AutoClear(context);
    }

    @Bean("dbInitRunner")
    @Primary
    public DbInitRunner getDbInitRunner(@Qualifier("autoClear") AutoClear autoClear) {
        return new DbInitRunner(context, autoClear, tidyConfig);
    }

}
