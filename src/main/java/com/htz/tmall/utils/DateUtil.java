package com.htz.tmall.utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 日期工具类，用于java.util.Date类与java.sql.Timestamp 类的互相转换。
 */
public class DateUtil {

    /**
     * java.util.Date --->  java.sql.TimeStamp
     * @param date
     * @return
     */
    public static Timestamp d2t(Date date){
        if(null == date){
            return null;
        }
        return new Timestamp(date.getTime());
    }

    /**
     * java.sql.TimeStamp  ---> java.util.Date
     * @param timestamp
     * @return
     */
    public static Date t2d(Timestamp timestamp){
        if(null == timestamp){
            return null;
        }
        return new Date(timestamp.getTime());
    }
}
