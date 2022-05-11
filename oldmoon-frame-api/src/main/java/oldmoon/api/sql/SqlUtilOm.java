package oldmoon.api.sql;

import oldmoon.api.json.JsonUtilOm;
import oldmoon.api.sql.model.Fie;
import oldmoon.api.sql.model.Table;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description sql处理工具类
 * @Author hupg
 * @Date 2021/9/16 18:15
 */
public class SqlUtilOm {

    /**
     * 解析ddl-sql语句-
     * @author hupg
     * @date 2021/9/16 18:20
     * @param ddl ddl语句
     * @return Table 表结构
     */
    public static Table regexDDL(String ddl) {
        // DDL基础解析
        String regex_table = ".*table\\s+(?<table>.*\\s+)\\((?<fields>.*),\\s+primary.*key.*\\((?<key>.*?)\\).*comment=(?<tableDesc>.*?);";
        ddl = ddl.toLowerCase();
        ddl = ddl.replaceAll("\r\n", "");
        ddl = ddl.replaceAll("\r", "");
        ddl = ddl.replaceAll("\n", "");
        if (ddl.contains("`")) {
            ddl = ddl.replaceAll("`", "");
        }
        Pattern compile = Pattern.compile(regex_table);
        Matcher matcher = compile.matcher(ddl);
        Map<String, String> dataMap = new HashMap<>();

        while (matcher.find()) {
            dataMap.put("table", matcher.group("table"));
            dataMap.put("fields", matcher.group("fields"));
            dataMap.put("key", matcher.group("key"));
            dataMap.put("tableDesc", matcher.group("tableDesc"));
        }

        Table table = new Table();
        Set<String> keys = dataMap.keySet();
        for (String key : keys) {
            String data = dataMap.get(key);

            if ("table".equals(key)) {
                // 解析表名
                data = data.trim();
                if (data.contains(".")) {
                    String[] split = data.split("\\.");
                    data = split[split.length - 1];
                }
                table.setName(data);
            } else if ("fields".equals(key)) {
                List<Fie> fieList = new ArrayList<>();
                // 解析列
                String regex_field = "\\s+(?<fieName>.*?)\\s+(?<fieType>.*?)\\s+.*comment\\s+'(?<fieDesc>.*?)'.*";
                Pattern patternFie = Pattern.compile(regex_field);
                String[] split = data.split(",");
                for (String fie : split) {
                    fie.trim();
                    Matcher fieMatcher = patternFie.matcher(fie);
                    while (fieMatcher.find()) {
                        Fie fieInfo = new Fie();
                        // 列名
                        fieInfo.setName(fieMatcher.group("fieName"));
                        // 列数据类型
                        fieInfo.setType(fieMatcher.group("fieType"));
                        // 列描述
                        fieInfo.setDesc(fieMatcher.group("fieDesc"));
                        fieList.add(fieInfo);
                    }
                }
                table.setFieList(fieList);
            } else if ("key".equals(key)) {
                // 解析主键
                data = data.trim();
                table.setKey(data);
            } else if ("tableDesc".equals(key)) {
                // 解析表描述
                data = data.trim();
                if (data.contains("'")) {
                    data = data.replaceAll("'", "");
                }
                table.setDesc(data);
            }
        }
        return table;
    }

//    public static void main(String[] args) {
//        String sql = "CREATE TABLE `abc`.`report_device` (\n" +
//                "  `id` int(11) NOT NULL COMMENT 'ID主键',\n" +
//                "  `type_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备类型编码',\n" +
//                "  `type_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备类型名称',\n" +
//                "  `device_mac` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备MAC',\n" +
//                "  `device_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备名称（蓝牙名称）',\n" +
//                "  `status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '报告状态：01待编辑；02已编辑；03已归档',\n" +
//                "  `status_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '报告状态名称',\n" +
//                "  `tested_time` datetime DEFAULT NULL COMMENT '测试完成时间',\n" +
//                "  `report_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '报告ID',\n" +
//                "  PRIMARY KEY (`id`)\n" +
//                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='测试报告--设备表（列表）';";
//
//        Table table = regexDDL(sql);
//        System.out.println(JsonUtilOm.objectToJsonString(table));
//    }

}
