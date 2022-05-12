package top.oldmoon.dbinit.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.oldmoon.dbinit.DbInitActuator;
import top.oldmoon.dbinit.config.TidyConfig;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 数据库初始化整体入口
 *
 * @author hupg
 * @date 2022/5/10 16:36
 */
@Order(BigDecimal.ROUND_HALF_EVEN)
@EnableConfigurationProperties({TidyConfig.class})
@Component
@Slf4j
public class DbInitRunner implements ApplicationRunner {
    @Resource
    ApplicationContext context;
    @Resource
    private TidyConfig tidyConfig;

    @Override
    public void run(ApplicationArguments args) {
        Map<String, DataSource> dataSourceMap = context.getBeansOfType(DataSource.class);
        Map<String, Boolean> dbs = tidyConfig.getDbs();
        dataSourceMap.forEach((key, dataSource) -> {
            if (dbs.get(key) != null && dbs.get(key)) {
                log.info("-=-=-=-=初始化数据库{}开始=-=-=-=-", key);
                DbInitActuator actuator = new DbInitActuator(key, dataSource);
                actuator.init();
                log.info("-=-=-=-=初始化数据库{}完成=-=-=-=-", key);
            }
        });
    }
}
