package io.github.dingdangdog.dbinit.runner;

import com.alibaba.fastjson.JSON;
import io.github.dingdangdog.dbinit.clear.AutoClear;
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
    private final AutoClear autoClear;
    private final DbInitConfig dbInitConfig;

    public DbInitRunner(ApplicationContext context, AutoClear autoClear, DbInitConfig dbInitConfig) {
        this.context = context;
        this.autoClear = autoClear;
        this.dbInitConfig = dbInitConfig;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<DbBase> dbList = dbInitConfig.getDbList();
        // 校验配置可用性
        if (!checkConfig(dbList)) {
            autoClear.clearAllByName(AutoClear.beanNameList);
            return;
        }
        Map<String, DbBase> nameForDb = dbList.stream()
                .filter(dbBase -> StringUtils.isNotEmpty(dbBase.getName()))
                .collect(Collectors.toMap(DbBase::getName, Function.identity()));

        // 获取spring容器中全部数据源
        Map<String, DataSource> dataSourceMap = context.getBeansOfType(DataSource.class);

        DbActuatorFactory factory = new DbActuatorFactory();
        dataSourceMap.forEach((key, dataSource) -> {
            DbBase dbBase = nameForDb.get(key);
            // 判断是否确认开启数据库初始化
            if (dbBase.getEnable()) {
                // 开启上下文
                DbInitContext dbInitContext = DbInitContextManager.begin(key);

                log.info("-=-=-=-=初始化数据库{}开始=-=-=-=-", key);
                factory.createActuator(key, dataSource, dbBase.getType()).init();
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
     * @author DDD
     * @date 2022/5/13 15:28
     */
    private boolean checkConfig(List<DbBase> dbBaseList) {
        if (CollectionUtils.isEmpty(dbBaseList)) {
            log.error("无数据源dbs或dbType配置，初始化停止！");
            return false;
        }
        AtomicBoolean right = new AtomicBoolean(true);
        dbBaseList.forEach(dbBase -> {
            String name = dbBase.getName();
            String type = dbBase.getType();
            if (StringUtils.isEmpty(type)) {
                right.set(false);
                log.error("{}数据源缺少dbType配置，初始化即将停止！", name);
            } else if (!DbInitConfig.supportType.contains(type.toUpperCase())) {
                right.set(false);
                log.error("{}数据源的dbType不支持，初始化即将停止！", name);
            }
        });
        return right.get();
    }
}
