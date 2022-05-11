package oldmoon.api.enums;

/**
 * @author hupg
 * @description http通信返回编码定义
 * @date 2022/2/21 14:20
 */
public enum HttpCode {
    /**
     * code：三位数2开头，为基本结果
     */
    SUCCESS(200, "SUCCESS！"),
    ERROR(201, "ERROR！"),
    /**
     * code：三位数3开头，为SQL执行失败错误
     */
    SQL_FAULT(300, "SQL FAULT！"),
    SELECT_FAULT(301, "SELECT FAULT！"),
    INSERT_FAULT(302, "INSERT FAULT！"),
    UPDATE_FAULT(303, "UPDATE FAULT！"),
    DELETE_FAULT(304, "DELETE FAULT！"),
    /**
     * code：三位数9开头，为未知结果
     */
    UNKNOWN_ERROR(999, "未知错误！");

    HttpCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
