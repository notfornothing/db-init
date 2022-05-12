package top.oldmoon.dbinit;

import top.oldmoon.dbinit.config.OracleConfig;

/**
 * 数据库初始化配置（Oracle）
 *
 * @author hupg
 * @date `2022/5/9` 13:33
 */
public class OracleInit implements InitInterface {
    OracleConfig config;

    public OracleInit(OracleConfig oracleConfig) {
        this.config = oracleConfig;
    }

    @Override
    public void init() {

    }

    @Override
    public void close() {

    }
}
