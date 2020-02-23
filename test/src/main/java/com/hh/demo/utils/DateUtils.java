package com.hh.demo.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateUtils {

    /**
     * 定义格式为：年-月-日 小时-分钟-秒
     */
    public static final String STANDARD="yyyy-MM--dd HH-mm-ss";

    /**
     * 将时间Date类型的时间转换为字符串
     * 类型为定义类型STANDARD
     */
    public static String date2Str(Date date){
        if (date == null){
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD);
    }

    /**
     * 将时间Date类型的时间转换为传入类型formate
     * @param date
     * 传入自定义时间格式
     * @param formate
     * @return
     */
    public static String date2Str(Date date,String formate){
        if (date == null){
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formate);
    }

    /**
     * 将字符串类型的时间转换为Date
     */
    public static Date srt2Date(String srtDate){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD);
        DateTime date = dateTimeFormatter.parseDateTime(srtDate);
        return date.toDate();
    }

}
