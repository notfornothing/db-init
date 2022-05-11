package top.oldmoon.dbinit.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.oldmoon.dbinit.MySqlInit;
import top.oldmoon.dbinit.config.MySqlConfig;
import top.oldmoon.dbinit.config.OracleConfig;

import java.math.BigDecimal;

/**
 * 数据库初始化整体入口
 *
 * @author hupg
 * @date 2022/5/10 16:36
 */
@Order(BigDecimal.ROUND_HALF_EVEN)
@EnableConfigurationProperties({MySqlConfig.class, OracleConfig.class})
@Component
@Slf4j
public class DbInitRunner implements ApplicationRunner {
    @Autowired
    private MySqlConfig mySqlConfig;
    @Autowired
    private OracleConfig oracleConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("-=-=-=-=初始化数据库{}开始=-=-=-=-", mySqlConfig.getUrl());
        MySqlInit mySqlInit = new MySqlInit(mySqlConfig);
        mySqlInit.init();
        log.info("-=-=-=-=初始化数据库{}完成=-=-=-=-", mySqlConfig.getUrl());
    }
}
