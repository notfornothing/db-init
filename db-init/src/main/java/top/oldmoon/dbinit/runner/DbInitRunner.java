package top.oldmoon.dbinit.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.oldmoon.dbinit.MySqlInit;
import top.oldmoon.dbinit.OracleInit;
import top.oldmoon.dbinit.config.MySqlConfig;
import top.oldmoon.dbinit.config.OracleConfig;

import javax.annotation.Resource;
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
    @Resource
    private MySqlConfig mySqlConfig;
    @Resource
    private OracleConfig oracleConfig;

    @Override
    public void run(ApplicationArguments args) {
        if (mySqlConfig.getEnable()) {
            log.info("-=-=-=-=初始化数据库{}开始=-=-=-=-", mySqlConfig.getUrl());
            MySqlInit mySqlInit = new MySqlInit(mySqlConfig);
            mySqlInit.init();
            log.info("-=-=-=-=初始化数据库{}完成=-=-=-=-", mySqlConfig.getUrl());
        }
        if (oracleConfig.getEnable()) {
            log.info("-=-=-=-=初始化数据库{}开始=-=-=-=-", oracleConfig.getUrl());
            OracleInit oracleInit = new OracleInit(oracleConfig);
            oracleInit.init();
            log.info("-=-=-=-=初始化数据库{}完成=-=-=-=-", oracleConfig.getUrl());
        }
    }
}
