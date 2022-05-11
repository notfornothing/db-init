package top.oldmoon.husoul.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * (ClockToday)实体类
 *
 * @author makejava
 * @since 2022-02-21 10:59:03
 */
@Setter
@Getter
public class ClockToday implements Serializable {
    private static final long serialVersionUID = -49558700083973961L;
    /**
     * 用户ID（主键）
     */
    private Integer userId;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 打卡内容
     */
    private String clockInfo;
    /**
     * 打卡日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime clockDate;

}

