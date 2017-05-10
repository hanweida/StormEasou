package com.easou.let.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-23
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 */
public class SimpleDateUtils {

    /**
     * Get current time string.
     *
     * @return the string
     */
    public static String getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * Date to string string.
     *
     * @param date the date
     * @return the string
     */
    public static String DateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 得到当年，返回年
     *
     * @return int
     */
    public static int getCurrentYear(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    /**
     * 得到年，返回年
     *
     * @param addYear the add year
     * @return int
     */
    public static int getYear(int addYear){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, addYear);
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println(SimpleDateUtils.getYear(-1));
    }
}
