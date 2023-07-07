package io.github.sliverkiss.utils;

import lombok.Builder;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/6
 */

public class DateUtil {
    private int year;
    private int month;
    private int day;

    //日期转字符串
    public static String dateToStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//如果需要显示时分秒："yyyy/MM/dd HH:mm:ss"
        return sdf.format(date);
    }

    public DateUtil() {
    }

    public DateUtil(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    //字符串转日期
    public static Date strToDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public  static DateUtil dateCompare(Date fromDate,Date toDate){
        Calendar from = Calendar.getInstance();
        from.setTime(fromDate);
        Calendar to = Calendar.getInstance();
        to.setTime(toDate);

        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int year = toYear-fromYear;
        int month = toYear * 12 + toMonth - (fromYear * 12 + fromMonth);
        int day = (int) ((to.getTimeInMillis() - from.getTimeInMillis() / (24 * 3600 * 1000)));
        return new DateUtil (day,month,year);
    }
}
