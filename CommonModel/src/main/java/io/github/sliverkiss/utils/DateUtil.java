package io.github.sliverkiss.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/6
 */

public class DateUtil {
    //日期转字符串
    public static String dateToStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//如果需要显示时分秒："yyyy/MM/dd HH:mm:ss"
        return sdf.format(date);
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
}
