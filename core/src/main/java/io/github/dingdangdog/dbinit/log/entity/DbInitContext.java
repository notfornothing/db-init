package io.github.dingdangdog.dbinit.log.entity;

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
     * 是否创建数据库：1是；2否
     */
    private Integer createDb;
    /**
     * 数据库名称
     */
    private String dbName;
    /**
     * 脚本文件名称
     */
    private String fileName;
    /**
     * sql语句数量
     */
    private Integer sqlNum;
    /**
     * 自动新建表数量
     */
    private Integer addTableNum;
    /**
     * 覆盖表数量
     */
    private Integer overwriteTableNum;
    /**
     * 跳过表数量
     */
    private Integer skipTableNum;
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

    @Override
    public String toString() {
        return "DbInitContext{" +
                "id=" + id +
                ", createDb=" + createDb +
                ", dbName='" + dbName + '\'' +
                ", addTableNum=" + addTableNum +
                ", fileName='" + fileName + '\'' +
                ", sqlNum=" + sqlNum +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", totalTimes=" + totalTimes +
                '}';
    }
}
