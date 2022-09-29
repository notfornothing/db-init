package io.github.dingdangdog.dbinit.constant;

/**
 * 此工具固定SQL
 *
 * @author DingDangDog
 * @since 2022/9/29 21:57
 */
@SuppressWarnings("all")
public class SQL {
    /**
     * MYSQL创建日志表SQL
     */
    private static final String CREATE_LOG_MYSQL = "CREATE TABLE `db_init_context_log` (" +
            "  `id` bigint NOT NULL COMMENT '上下文ID'," +
            "  `create_db` int DEFAULT NULL COMMENT '是否创建数据库：1是；2否'," +
            "  `db_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '数据库名称'," +
            "  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '脚本文件名称'," +
            "  `sql_num` int DEFAULT NULL COMMENT 'sql数量'," +
            "  `add_table_num` int DEFAULT NULL COMMENT '自动新建表数量'," +
            "  `overwrite_table_num` int DEFAULT NULL COMMENT '覆盖表数量'," +
            "  `skip_table_num` int DEFAULT NULL COMMENT '跳过表数量'," +
            "  `begin_time` datetime DEFAULT NULL COMMENT '任务开始时间'," +
            "  `end_time` datetime DEFAULT NULL COMMENT '任务结束时间'," +
            "  `total_times` bigint DEFAULT NULL COMMENT '任务总时长'," +
            "  PRIMARY KEY (`id`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;";
}
