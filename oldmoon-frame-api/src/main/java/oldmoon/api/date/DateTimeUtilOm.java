package oldmoon.api.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Description 时间处理工具类
 * @Author hupg
 * @Date 2021/8/17 16:53
 */
public class DateTimeUtilOm {

    /**********************************************************
     **********        以下为  JDK8以后推荐操作       ***********
     *********************************************************/

    /**
     * @Description LocalDate 转 字符串，默认格式为"yyyy-MM-dd"
     * @Author hupg
     * @Date 2021/8/17 16:56
     */
    public static String localDateToString(LocalDate date) {
        return localDateToString(date, "yyyy-MM-dd");
    }

    /**
     * @Description LocalDate 转 字符串，指定输出格式
     * @Author hupg
     * @Date 2021/8/17 16:57
     */
    public static String localDateToString(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(date);
    }

    /**
     * @Description LocalTime 转 字符串，默认格式为"HH:mm:ss"
     * @Author hupg
     * @Date 2021/8/17 16:56
     */
    public static String localTimeToString(LocalTime time) {
        return localTimeToString(time, "HH:mm:ss");
    }

    /**
     * @Description LocalTime 转 字符串，指定输出格式
     * @Author hupg
     * @Date 2021/8/17 16:57
     */
    public static String localTimeToString(LocalTime time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(time);
    }

    /**
     * @Description LocalDateTime 转 字符串，默认格式为"yyyy-MM-dd HH:mm:ss"
     * @Author hupg
     * @Date 2021/8/17 16:56
     */
    public static String localDateTimeToString(LocalDateTime dateTime) {
        return localDateTimeToString(dateTime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @Description LocalDateTime 转 字符串，指定输出格式
     * @Author hupg
     * @Date 2021/8/17 16:57
     */
    public static String localDateTimeToString(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(dateTime);
    }

    /**
     * @Description 字符串 转 LocalDateTime，默认"yyyy-MM-dd HH:mm:ss"
     * @Author hupg
     * @Date 2021/8/17 16:57
     */
    public static LocalDateTime toLocalDateTime(String str) throws ParseException {
        return toLocalDateTime(str, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @Description 字符串 转 LocalDateTime，指定字符格式
     * @Author hupg
     * @Date 2021/8/17 16:57
     */
    public static LocalDateTime toLocalDateTime(String str, String pattern) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(str, formatter);
    }

    /**
     * @Description Date 转为 LocalDateTime
     * @Author hupg
     * @Date 2021/8/17 16:53
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**********************************************************
     **********        以下为    JDK8以前操作        ***********
     *********************************************************/

    /**
     * @Description Date 转 字符串，默认格式为"yyyy-MM-dd HH:mm:ss"
     * @Author hupg
     * @Date 2021/8/17 16:56
     */
    public static String dateToString(Date date) {
        return dateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @Description Date 转 字符串，指定输出格式
     * @Author hupg
     * @Date 2021/8/17 16:57
     */
    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * @Description 字符串 转 Date，默认"yyyy-MM-dd HH:mm:ss"
     * @Author hupg
     * @Date 2021/8/17 16:57
     */
    public static Date toDate(String str) throws ParseException {
        return toDate(str, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @Description 字符串 转 Date，指定字符格式
     * @Author hupg
     * @Date 2021/8/17 16:57
     */
    public static Date toDate(String str, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(str);
    }

}
