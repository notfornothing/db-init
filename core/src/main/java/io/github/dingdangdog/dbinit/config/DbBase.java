package io.github.dingdangdog.dbinit.config;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据源基本配置
 *
 * @author DDD
 * @since 2022/5/18 15:42
 */
@Setter
@Getter
public class DbBase {
    /**
     * 数据源名称
     */
    private String name;
    /**
     * 是否初始化
     */
    private Boolean enable;
    /**
     * 数据库类型
     */
    private String type;
    /**
     * 是否记录日志
     */
    private Boolean logging;
}
