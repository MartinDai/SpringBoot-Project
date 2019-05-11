package com.doodl6.springboot.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

    /**
     * 工具类不需要构造函数
     */
    private DateUtil() {
    }

    //----------常用日期时间格式化对象----------

    //SimpleDateFormat是线程不安全的，使用以下常量操作必须保证外部同步

    public static final SimpleDateFormat DATE_MINUTE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat YEAR_MONTH_FORMAT = new SimpleDateFormat("yyyy-MM");

    /**
     * 返回当前的时间 yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDateTime() {
        synchronized (DATE_TIME_FORMAT) {
            return DATE_TIME_FORMAT.format(new Date());
        }
    }

    /**
     * 将传入的日期转换成 yyyy-MM-dd HH:mm的形式
     */
    public static String getDateMinute(Date date) {
        synchronized (DATE_MINUTE_FORMAT) {
            return DATE_MINUTE_FORMAT.format(date);
        }
    }

    /**
     * 将传入的日期转换成 yyyy-MM-dd HH:mm:ss的形式
     */
    public static String getDateTime(Date date) {
        synchronized (DATE_TIME_FORMAT) {
            return DATE_TIME_FORMAT.format(date);
        }
    }

    /**
     * 将传入的long转换成 yyyy-MM-dd HH:mm:ss的形式
     */
    public static String getDateTime(long time) {
        synchronized (DATE_TIME_FORMAT) {
            return DATE_TIME_FORMAT.format(time);
        }
    }

    /**
     * 返回当前日期 yyyy-MM-dd
     */
    public static String getNowDate() {
        synchronized (DATE_FORMAT) {
            return DATE_FORMAT.format(new Date());
        }
    }

    /**
     * 将传入的日期转换成 yyyy-MM-dd的形式
     */
    public static String getDate(Date date) {
        synchronized (DATE_FORMAT) {
            return DATE_FORMAT.format(date);
        }
    }

    /**
     * 将传入的long转换成 yyyy-MM-dd的形式
     */
    public static String getDate(long time) {
        synchronized (DATE_FORMAT) {
            return DATE_FORMAT.format(time);
        }
    }

    /**
     * 返回当前的年月 yyyy-MM
     */
    public static String getNowYearMonth() {
        synchronized (YEAR_MONTH_FORMAT) {
            return YEAR_MONTH_FORMAT.format(new Date());
        }
    }

    /**
     * 将传入的日期转换成 yyyy-MM形式
     */
    public static String getYearMonth(Date date) {
        synchronized (YEAR_MONTH_FORMAT) {
            return YEAR_MONTH_FORMAT.format(date);
        }
    }

    /**
     * 将传入的long转换成 yyyy-MM形式
     */
    public static String getYearMonth(long time) {
        synchronized (YEAR_MONTH_FORMAT) {
            return YEAR_MONTH_FORMAT.format(time);
        }
    }

    /**
     * 得到自定义格式的当前时间字符串
     *
     * @throws IllegalArgumentException 如果输入的pattern不正确
     */
    public static String getNowByPattern(String pattern)
            throws IllegalArgumentException {
        try {
            return new SimpleDateFormat(pattern).format(new Date());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("输入参数不正确", e);
        }
    }

    /**
     * 得到自定义格式的特定时间字符串
     *
     * @throws IllegalArgumentException 如果输入的pattern不正确
     */
    public static String getByPattern(String pattern, Date date)
            throws IllegalArgumentException {
        try {
            if (date == null) {
                return "";
            }
            return new SimpleDateFormat(pattern).format(date);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("输入参数不正确", e);
        }
    }

    /**
     * 得到自定义格式的特定long时间的字符串
     *
     * @throws IllegalArgumentException 如果输入的pattern不正确
     */
    public static String getByPattern(String pattern, long time)
            throws IllegalArgumentException {
        try {
            return new SimpleDateFormat(pattern).format(time);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("输入参数不正确", e);
        }
    }

    /**
     * 将格式为 yyyy-MM-dd 形式的字符串转换成日期
     */
    public static Date parseDate(String source) throws ParseException {
        synchronized (DATE_FORMAT) {
            try {
                return DATE_FORMAT.parse(source);
            } catch (ParseException e) {
                throw new ParseException("期待 yyyy-MM-dd 格式的日期,但输入格式为: " + source, e.getErrorOffset());
            }
        }
    }

    /**
     * 将格式为 yyyy-MM-dd HH:mm形式的字符串转换成日期
     */
    public static Date parseDateMinite(String source) throws ParseException {
        synchronized (DATE_MINUTE_FORMAT) {
            try {
                return DATE_MINUTE_FORMAT.parse(source);
            } catch (ParseException e) {
                throw new ParseException("期待 yyyy-MM-dd HH:mm格式的日期,但输入格式为: " + source, e.getErrorOffset());
            }
        }
    }

    /**
     * 将格式为 yyyy-MM-dd HH:mm:ss 的字符串转换成日期
     */
    public static Date parseDateTime(String source) throws ParseException {
        synchronized (DATE_TIME_FORMAT) {
            try {
                return DATE_TIME_FORMAT.parse(source);
            } catch (ParseException e) {
                throw new ParseException("期待 yyyy-MM-dd HH:mm:ss  格式的日期,但输入格式为: "
                        + source, e.getErrorOffset());
            }
        }
    }

    /**
     * 得到与传入时间date，在字段offsetField上相差offset的date
     *
     * @param offsetField Calendar类的field
     */
    public static Date getDistanceDate(Date date, int offset, int offsetField) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(offsetField, c.get(offsetField) + offset);
        return c.getTime();
    }

    /**
     * 得到两个日期之间查几天。
     */
    public static int getOffsetIn2Date(Date date1, Date date2) {
        long offsetNum = date1.getTime() - date2.getTime();
        return (int) (offsetNum / (60 * 60 * 24 * 1000));
    }

    /**
     * 两天之前差多少分钟
     */
    public static int getOffsetMinIn2Date(Date date1, Date date2) {
        long offsetNum = date1.getTime() - date2.getTime();
        return ((int) (offsetNum / (60 * 1000)));
    }

    /**
     * 比较时间  0 相等  ,1 date1在date2之后 也就是date1是晚些时间 -1 与1相反
     */
    public static int compareDate(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 1;
        }

        try {
            if (date1.getTime() > date2.getTime()) {
                return 1;
            } else if (date1.getTime() < date2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception ignored) {
        }
        return 0;
    }

    /**
     * 获取今天最开始的时间
     */
    public static Date getTodayFirstTime() {
        return getFirstTime(new Date());
    }

    /**
     * 获得指定日期当天最开始的时间
     */
    public static Date getFirstTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得指定日期当天最后的时间
     */
    public static Date getLastTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获得两个时间中较小的那个，null表示无限大
     */
    public static Date minDate(Date date1, Date date2) {
        if (date1 == null) {
            return date2;
        } else if (date2 == null) {
            return date1;
        } else {
            if (date1.getTime() < date2.getTime()) {
                return date1;
            } else {
                return date2;
            }
        }
    }

    /**
     * 获得两个时间中较大的那个，null表示无限小
     */
    public static Date maxDate(Date date1, Date date2) {
        if (date1 == null) {
            return date2;
        } else if (date2 == null) {
            return date1;
        } else {
            if (date1.getTime() > date2.getTime()) {
                return date1;
            } else {
                return date2;
            }
        }
    }

    public static void main(String[] args) throws ParseException {
        String deadline = "2016-07-29";
        Date date = DATE_FORMAT.parse(deadline);
        System.out.println(DATE_TIME_FORMAT.format(date));
        System.out.println(getDateTime(1472020435000L));
    }

}
