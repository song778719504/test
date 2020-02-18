package com.hh.demo.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateUtils {

    public static final String STANDARD="yyyy-MM--dd HH-mm-ss";

    /**
     * 将时间Date类型的时间转换为字符串
     */

    public static String date2Str(Date date){
        if (date == null){
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD);
    }

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
