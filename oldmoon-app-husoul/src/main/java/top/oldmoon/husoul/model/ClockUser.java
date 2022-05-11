package top.oldmoon.husoul.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * (ClockUser)实体类
 *
 * @author makejava
 * @since 2022-02-21 10:59:03
 */
@Setter
@Getter
public class ClockUser implements Serializable {
    private static final long serialVersionUID = 978624349991757145L;
    /**
     * 用户ID（主键）
     */
    private Integer userId;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 创建时间
     * DateTimeFormat：入参字符串格式化为时间类型
     * JsonFormat：入参时间类型格式化为字符串，东八区 +8小时
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime createDate;
    /**
     * 最后活动时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime lastActiveDate;

}

