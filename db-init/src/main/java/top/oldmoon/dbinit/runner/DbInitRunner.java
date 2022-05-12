package top.oldmoon.dbinit.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.oldmoon.dbinit.DbInitActuator;
import top.oldmoon.dbinit.clear.AutoClear;
import top.oldmoon.dbinit.config.TidyConfig;
import top.oldmoon.dbinit.log.entity.DbInitContext;
import top.oldmoon.dbinit.log.manager.DbInitContextManager;

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
    AutoClear autoClear;
    @Resource
    private TidyConfig tidyConfig;

    @Override
    public void run(ApplicationArguments args) {
        // 获取spring容器中全部数据源
        Map<String, DataSource> dataSourceMap = context.getBeansOfType(DataSource.class);
        Map<String, Boolean> dbs = tidyConfig.getDbs();
        dataSourceMap.forEach((key, dataSource) -> {
            // 开启上下文
            DbInitContext dbInitContext = DbInitContextManager.begin();
            dbInitContext.setDbName(key);
            // 判断是否确认开启数据库初始化
            if (dbs.get(key) != null && dbs.get(key)) {
                log.info("-=-=-=-=初始化数据库{}开始=-=-=-=-", key);
                // 创建初始化执行器
                DbInitActuator actuator = new DbInitActuator(key, dataSource);
                actuator.init();
                log.info("-=-=-=-=初始化数据库{}完成=-=-=-=-", key);
            }
            // 关闭上下文
            DbInitContextManager.end();
            // 打印验证上下文记录情况
            // System.out.println(JSON.toJSONString(dbInitContext));
        });
        autoClear.clearAllByName(AutoClear.beanNameList);
    }
}
