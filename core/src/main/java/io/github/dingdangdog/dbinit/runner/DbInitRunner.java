package io.github.dingdangdog.dbinit.runner;

import io.github.dingdangdog.dbinit.clear.AutoClearListener;
import io.github.dingdangdog.dbinit.config.DbBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import io.github.dingdangdog.dbinit.actuator.factory.DbActuatorFactory;
import io.github.dingdangdog.dbinit.config.DbInitConfig;
import io.github.dingdangdog.dbinit.log.entity.DbInitContext;
import io.github.dingdangdog.dbinit.log.manager.DbInitContextManager;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 数据库初始化入口
 *
 * @author DingDangDog
 * @since 2022/5/10 16:36
 */
@Order(BigDecimal.ROUND_HALF_EVEN)
@Slf4j
public class DbInitRunner implements ApplicationRunner {
    private final ApplicationContext context;
    private final AutoClearListener autoClearListener;
    private final DbInitConfig dbInitConfig;

    public DbInitRunner(ApplicationContext context, AutoClearListener autoClearListener, DbInitConfig dbInitConfig) {
        this.context = context;
        this.autoClearListener = autoClearListener;
        this.dbInitConfig = dbInitConfig;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("--------DDD----DbInit Runner Begin----DDD--------");
        List<DbBase> dbList = dbInitConfig.getDbList();
        // 校验配置可用性
        if (!checkConfig(dbList)) {
            autoClearListener.clearAllByName(AutoClearListener.beanNameList);
            return;
        }
        Map<String, DbBase> nameForDb = dbList.stream()
                .filter(dbBase -> StringUtils.isNotEmpty(dbBase.getName()))
                .collect(Collectors.toMap(DbBase::getName, Function.identity()));
        log.info("--------DDD---- DbInit Datasource: {} ----DDD--------", nameForDb.keySet());
        // 获取spring容器中全部数据源
        Map<String, DataSource> dataSourceMap = context.getBeansOfType(DataSource.class);
        DbActuatorFactory factory = new DbActuatorFactory();
        dataSourceMap.forEach((key, dataSource) -> {
            DbBase dbBase = nameForDb.get(key);
            // 判断是否确认开启数据库初始化
            if (dbBase.getEnable()) {
                // 开启上下文
                DbInitContext dbInitContext = DbInitContextManager.begin(key);
                log.info("--------DDD---- DbInit {} Begin ----DDD--------", key);
                factory.createActuator(key, dataSource, dbBase).init();
                // 关闭上下文
                DbInitContextManager.end();
                // 输出日志
                log.info("--------DDD---- DbInit {} Info: {} ----DDD--------", key, dbInitContext.toString());
                log.info("--------DDD---- DbInit {} End ----DDD--------", key);
            }
        });
        autoClearListener.clearAllByName(AutoClearListener.beanNameList);
    }

    /**
     * 校验配置可用性
     *
     * @return boolean 配置可用性
     * <ul>
     * <li>true：可用</li>
     * <li>false：不可用</li>
     * </ul>
     * @author DDD
     * @date 2022/5/13 15:28
     */
    private boolean checkConfig(List<DbBase> dbBaseList) {
        if (CollectionUtils.isEmpty(dbBaseList)) {
            log.error("--------DDD---- Undefined Datasource Config, DbInit End! ----DDD--------");
            return false;
        }
        AtomicBoolean right = new AtomicBoolean(true);
        dbBaseList.forEach(dbBase -> {
            String name = dbBase.getName();
            String type = dbBase.getType();
            if (StringUtils.isEmpty(type)) {
                log.error("--------DDD---- Datasource {} Missing Config: type, DbInit Will Stop! ----DDD--------", name);
                right.set(false);
            } else if (!DbInitConfig.supportType.contains(type.toUpperCase())) {
                log.error("--------DDD---- Datasource {} type: {} Not Supported, DbInit Will Stop! ----DDD--------", name, type);
                right.set(false);
            }
            // 校验创建数据库所需参数
            if (dbBase.getCreate()) {
                List<String> missingConfig = new ArrayList<>();
                if (StringUtils.isEmpty(dbBase.getUrl())) {
                    missingConfig.add("url");
                }
                if (StringUtils.isEmpty(dbBase.getBaseName())) {
                    missingConfig.add("baseName");
                }
                if (StringUtils.isEmpty(dbBase.getUsername())) {
                    missingConfig.add("username");
                }
                if (StringUtils.isEmpty(dbBase.getPassword())) {
                    missingConfig.add("password");
                }
                if (!CollectionUtils.isEmpty(missingConfig)) {
                    log.error("--------DDD---- Datasource {} Missing Config {} ----DDD--------", name, missingConfig);
                    right.set(false);
                }
            }
        });
        return right.get();
    }
}
