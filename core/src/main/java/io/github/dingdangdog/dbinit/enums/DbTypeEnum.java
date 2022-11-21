package io.github.dingdangdog.dbinit.enums;

/**
 * 数据源类型枚举类
 *
 * @author DingDangDog
 * @date 2022/11/21
 */
public enum DbTypeEnum {
    /**
     * mysql
     */
    MYSQL("mysql", "8.0");

    DbTypeEnum(String type, String version) {
        this.type = type;
        this.version = version;
    }

    private String type;
    private String version;

    public String getType() {
        return type;
    }


    public String getVersion() {
        return version;
    }
}
