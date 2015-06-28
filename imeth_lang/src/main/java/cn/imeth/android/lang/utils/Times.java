package cn.imeth.android.lang.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.imeth.android.lang.log.Log;

/**
 * Created by 戴文龙(daiwenlong@hequ.com)on 15/6/11.
 */
public class Times {

    public static long oneHourMillis = 60 * 60 * 1000; // 一小时的毫秒数
    public static long oneDayMillis = 24 * oneHourMillis; // 一天的毫秒数
    public static long oneYearMillis = 365 * oneDayMillis; // 一年的毫秒数

    /**
     * 把日期毫秒转化为字符串。默认格式：yyyy-MM-dd HH:mm:ss。
     *
     * @param millis 要转化的日期毫秒数。
     * @return 返回日期字符串（如："2013-02-19 11:48:31"）。
     * @author wangjie
     */
    public static String millisToStringDate(long millis) {
        return millisToStringDate(millis, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 把日期毫秒转化为字符串。
     *
     * @param millis  要转化的日期毫秒数。
     * @param pattern 要转化为的字符串格式（如：yyyy-MM-dd HH:mm:ss）。
     * @return 返回日期字符串。
     * @author wangjie
     */
    public static String millisToStringDate(long millis, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(millis));

    }

    /**
     * 把日期毫秒转化为字符串（文件名）。
     *
     * @param millis  要转化的日期毫秒数。
     * @param pattern 要转化为的字符串格式（如：yyyy-MM-dd HH:mm:ss）。
     * @return 返回日期字符串（yyyy_MM_dd_HH_mm_ss）。
     * @author wangjie
     */
    public static String millisToStringFilename(long millis, String pattern) {
        String dateStr = millisToStringDate(millis, pattern);
        return dateStr.replaceAll("[- :]", "_");
    }

    /**
     * 把日期毫秒转化为字符串（文件名）。
     *
     * @param millis 要转化的日期毫秒数。
     * @return 返回日期字符串（yyyy_MM_dd_HH_mm_ss）。
     * @author wangjie
     */
    public static String millisToStringFilename(long millis) {
        String dateStr = millisToStringDate(millis, "yyyy-MM-dd HH:mm:ss");
        return dateStr.replaceAll("[- :]", "_");
    }


    public static Date string2Date(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            Log.e("imeth times", e);
        }
        return date;

    }

    /**
     * 字符串解析成毫秒数
     *
     * @param str
     * @param pattern
     * @return
     */
    public static long string2Millis(String str, String pattern) {
        long millis = 0;
        try {
            millis = string2Date(str, pattern).getTime();
        } catch (Exception e) {
            Log.e("imeth times", e);
        }
        return millis;
    }

}
