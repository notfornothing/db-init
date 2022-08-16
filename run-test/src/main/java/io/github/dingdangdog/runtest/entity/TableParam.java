package io.github.dingdangdog.runtest.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 动态列表查询条件格式封装
 *
 * @author DingDangDog
 * @since 2022/8/16 14:24
 */
@Setter
@Getter
public class TableParam {
    /**
     * 库名
     */
    private String database;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 是否需要分页：true是；false否
     */
    private Boolean needPage;
    /**
     * 页码
     */
    private String pageNum;
    /**
     * 数量
     */
    private String pageSize;
    /**
     * 列表正常查询条件
     */
    private Map<String, Object> param;
}
