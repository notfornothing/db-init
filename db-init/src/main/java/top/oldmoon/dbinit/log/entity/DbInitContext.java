package top.oldmoon.dbinit.log.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据初始化日志信息
 *
 * @author DDD
 * @since 2022/5/12 15:13
 */
@Getter
@Setter
public class DbInitContext implements Serializable {
    private static final long serialVersionUID = 3178974471224359570L;
    /**
     * 日志ID
     */
    private Long id;
    /**
     * 数据库名称
     */
    private String dbName;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * sql语句数量
     */
    private Integer sqlNumber;
    /**
     * 开始时间
     */
    private LocalDateTime beginTime;
    /**
     * 结束
     */
    private LocalDateTime endTime;
    /**
     * 总时长
     */
    private Long totalTimes;
}
