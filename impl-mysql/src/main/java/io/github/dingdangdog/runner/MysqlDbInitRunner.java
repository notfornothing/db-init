package io.github.dingdangdog.runner;

import io.github.dingdangdog.actuator.impl.MySqlActuator;
import io.github.dingdangdog.config.DbBase;
import io.github.dingdangdog.config.DbInitConfig;
import io.github.dingdangdog.entity.DbInitContext;
import io.github.dingdangdog.manager.DbInitContextManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * MySQL初始化runner
 *
 * @author DingDangDog
 * @since 2022/10/26 17:32
 */
@Order(BigDecimal.ROUND_HALF_EVEN)
@Slf4j
public class MysqlDbInitRunner implements DbInitRunner {
    private final ApplicationContext context;
    private final DbInitConfig dbInitConfig;

    public MysqlDbInitRunner(ApplicationContext context, DbInitConfig dbInitConfig) {
        this.context = context;
        this.dbInitConfig = dbInitConfig;
    }

    @Override
    public void init() throws Exception {
        log.info("--------DDD---- DbInit Runner Begin ----DDD--------");
        List<DbBase> dbList = dbInitConfig.getDbList();
        // 校验配置可用性
        Map<String, DbBase> nameForDb = dbList.stream()
                .filter(dbBase -> StringUtils.isNotEmpty(dbBase.getName()))
                .collect(Collectors.toMap(DbBase::getName, Function.identity()));
        log.info("--------DDD---- DbInit Datasource: {} ----DDD--------", nameForDb.keySet());
        // 获取spring容器中全部数据源
        Map<String, DataSource> dataSourceMap = context.getBeansOfType(DataSource.class);
        // TODO 限制mysql数据库执行，且一次执行一个，去掉for循环
        dataSourceMap.forEach((key, dataSource) -> {
            DbBase dbBase = nameForDb.get(key);
            // 判断是否确认开启数据库初始化
            if (dbBase != null && dbBase.getEnable()) {
                // 开启上下文
                DbInitContext dbInitContext = DbInitContextManager.begin(key);
                log.info("--------DDD---- DbInit {} Begin ----DDD--------", key);
                new MySqlActuator(key, dataSource, dbBase).init();
                // 关闭上下文
                DbInitContextManager.end();
                // 输出日志
                log.info("--------DDD---- DbInit {} Info: {} ----DDD--------", key, dbInitContext.toString());
                log.info("--------DDD---- DbInit {} End ----DDD--------", key);
            }
        });

        log.info("--------DDD---- DbInit Runner End ----DDD--------");
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
    @Override
    public boolean checkConfig() {
        List<DbBase> dbBaseList = dbInitConfig.getDbList();
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
