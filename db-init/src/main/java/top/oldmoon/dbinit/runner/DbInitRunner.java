package top.oldmoon.dbinit.runner;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import top.oldmoon.dbinit.actuator.DefaultActuator;
import top.oldmoon.dbinit.actuator.factory.DbActuatorFactory;
import top.oldmoon.dbinit.clear.AutoClear;
import top.oldmoon.dbinit.config.TidyConfig;
import top.oldmoon.dbinit.log.entity.DbInitContext;
import top.oldmoon.dbinit.log.manager.DbInitContextManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 数据库初始化整体入口
 *
 * @author hupg
 * @date 2022/5/10 16:36
 */
@Order(BigDecimal.ROUND_HALF_EVEN)
@Slf4j
public class DbInitRunner implements ApplicationRunner {
    private final ApplicationContext context;
    private final AutoClear autoClear;
    private final TidyConfig tidyConfig;

    public DbInitRunner(ApplicationContext context, AutoClear autoClear, TidyConfig tidyConfig) {
        this.context = context;
        this.autoClear = autoClear;
        this.tidyConfig = tidyConfig;
    }

    private Map<String, Boolean> dbs;
    private Map<String, String> dbType;

    @Override
    public void run(ApplicationArguments args) {
        // 校验配置可用性
        if (!checkConfig()) {
            autoClear.clearAllByName(AutoClear.beanNameList);
            return;
        }

        // 获取spring容器中全部数据源
        Map<String, DataSource> dataSourceMap = context.getBeansOfType(DataSource.class);

        DbActuatorFactory factory = new DbActuatorFactory();
        dataSourceMap.forEach((key, dataSource) -> {
            // 判断是否确认开启数据库初始化
            if (dbs.get(key) != null && dbs.get(key)) {
                // 开启上下文
                DbInitContext dbInitContext = DbInitContextManager.begin(key);

                log.info("-=-=-=-=初始化数据库{}开始=-=-=-=-", key);
                DefaultActuator actuator = factory.createActuator(key, dataSource, dbType.get(key));
                actuator.init();
                log.info("-=-=-=-=初始化数据库{}完成=-=-=-=-", key);

                // 关闭上下文
                DbInitContextManager.end();
                // 打印上下文，验证记录情况
                System.out.println(JSON.toJSONString(dbInitContext));
            }
        });
        autoClear.clearAllByName(AutoClear.beanNameList);
    }

    /**
     * 校验配置可用性
     *
     * @return boolean 配置可用性
     * <ul>
     * <li>true：可用</li>
     * <li>false：不可用</li>
     * </ul>
     * @author hupg
     * @date 2022/5/13 15:28
     */
    private boolean checkConfig() {
        dbs = tidyConfig.getDbs();
        dbType = tidyConfig.getDbType();
        if (CollectionUtils.isEmpty(dbs) || CollectionUtils.isEmpty(dbType)) {
            log.error("无数据源dbs或dbType配置，初始化停止！");
            return false;
        }
        AtomicBoolean right = new AtomicBoolean(true);
        dbs.forEach((key, value) -> {
            String type = dbType.get(key);
            if (StringUtils.isEmpty(type)) {
                right.set(false);
                log.error("{}数据源缺少dbType配置，初始化即将停止！", key);
            } else if (!TidyConfig.supportType.contains(type.toUpperCase())) {
                right.set(false);
                log.error("{}数据源的dbType不支持，初始化即将停止！", key);
            }
        });
        return right.get();
    }
}
