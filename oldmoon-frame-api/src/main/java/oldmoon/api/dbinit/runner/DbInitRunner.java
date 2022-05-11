package oldmoon.api.dbinit.runner;

import oldmoon.api.dbinit.MySqlInit;
import oldmoon.api.dbinit.config.MySqlConfig;
import oldmoon.api.dbinit.config.OracleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;

/**
 * 数据库初始化整体入口
 *
 * @author hupg
 * @date 2022/5/10 16:36
 */
@Order(BigDecimal.ROUND_HALF_EVEN)
@EnableConfigurationProperties({MySqlConfig.class, OracleConfig.class})
public class DbInitRunner implements ApplicationRunner {
    @Autowired
    private MySqlConfig mySqlConfig;
    @Autowired
    private OracleConfig oracleConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        MySqlInit mySqlInit = new MySqlInit(mySqlConfig);
        mySqlInit.init();
    }
}
