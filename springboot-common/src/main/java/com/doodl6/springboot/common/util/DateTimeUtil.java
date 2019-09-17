package com.doodl6.springboot.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 日期时间工具类
 */
public class DateTimeUtil {

    /**
     * 工具类不需要构造函数
     */
    private DateTimeUtil() {
    }

    //----------常用日期时间格式化对象----------

    private static final DateTimeFormatter DATE_MINUTE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 返回当前的时间 yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDateTime() {
        return getDateTime(LocalDateTime.now());
    }

    /**
     * 将传入的日期转换成 yyyy-MM-dd HH:mm的形式
     */
    public static String getDateMinute(LocalDateTime dateTime) {
        return DATE_MINUTE_FORMATTER.format(dateTime);
    }

    /**
     * 将传入的日期转换成 yyyy-MM-dd HH:mm:ss的形式
     */
    public static String getDateTime(LocalDateTime dateTime) {
        return DATE_TIME_FORMATTER.format(dateTime);
    }

    /**
     * 将传入的毫秒时间戳转换成 yyyy-MM-dd HH:mm:ss的形式
     */
    public static String getDateTime(long milli) {
        return DATE_TIME_FORMATTER.format(Instant.ofEpochMilli(milli).atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    /**
     * 返回当前日期 yyyy-MM-dd
     */
    public static String getNowDate() {
        return LocalDate.now().toString();
    }

    /**
     * 将传入的日期转换成 yyyy-MM-dd的形式
     */
    public static String getDate(LocalDate date) {
        return date.toString();
    }

    /**
     * 将传入的毫秒时间戳转换成 yyyy-MM-dd的形式
     */
    public static String getDate(long milli) {
        return Instant.ofEpochMilli(milli).atZone(ZoneId.systemDefault()).toLocalDate().toString();
    }

    /**
     * 返回当前的年月 yyyy-MM
     */
    public static String getNowYearMonth() {
        return YearMonth.now().toString();
    }

    /**
     * 将传入的日期转换成 yyyy-MM形式
     */
    public static String getYearMonth(LocalDate date) {
        return YearMonth.of(date.getYear(),date.getMonth()).toString();
    }

    /**
     * 将传入的毫秒时间戳转换成 yyyy-MM形式
     */
    public static String getYearMonth(long milli) {
        return Instant.ofEpochMilli(milli).atZone(ZoneId.systemDefault()).toLocalDate().toString();
    }

    /**
     * 将格式为 yyyy-MM-dd 形式的字符串转换成日期
     */
    public static LocalDate parseDate(String source) {
        return DATE_FORMATTER.parse(source, LocalDate::from);
    }

    /**
     * 将格式为 yyyy-MM-dd HH:mm:ss 的字符串转换成日期
     */
    public static LocalDateTime parseDateTime(String source) {
        return DATE_TIME_FORMATTER.parse(source, LocalDateTime::from);
    }

    /**
     * 获取今天最开始的时间
     */
    public static LocalDateTime getTodayFirstTime() {
        return getFirstTime(LocalDateTime.now());
    }

    /**
     * 获得指定日期当天最开始的时间
     */
    public static LocalDateTime getFirstTime(LocalDateTime dateTime) {
        return dateTime.with(LocalTime.of(0, 0));
    }

    /**
     * 获得指定日期当天最后的时间
     */
    public static LocalDateTime getLastTime(LocalDateTime dateTime) {
        return dateTime.with(LocalTime.of(23, 59, 59, 999_999_999));
    }

    /**
     * 获取两个时间之间相差天数
     */
    public static long getUntilBetween2Time(LocalDate from, LocalDate to) {
        return from.until(to, ChronoUnit.DAYS);
    }

    public static void main(String[] args) {
        System.out.println(getNowDateTime());

        System.out.println(getDateMinute(LocalDateTime.now()));

        System.out.println(getDateTime(Instant.now().toEpochMilli()));

        System.out.println(getNowDate());

        System.out.println(getDate(LocalDate.now()));

        System.out.println(getDate(Instant.now().toEpochMilli()));

        System.out.println(getNowYearMonth());

        System.out.println(getYearMonth(LocalDate.now()));

        System.out.println(getYearMonth(Instant.now().toEpochMilli()));

        System.out.println(parseDate("2019-09-17"));

        System.out.println(getDateTime(parseDateTime("2019-09-17 22:22:22")));

        System.out.println(getDateTime(getTodayFirstTime()));

        System.out.println(getDateTime(getLastTime(LocalDateTime.now())));

        System.out.println(getDateTime(getLastTime(LocalDateTime.now())));

        LocalDate from = LocalDate.of(2018, 2, 1);
        LocalDate to = LocalDate.of(2019, 2, 2);
        System.out.println(getUntilBetween2Time(from, to));

    }

}
