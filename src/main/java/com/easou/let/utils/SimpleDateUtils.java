package com.easou.let.utils;

import java.text.SimpleDateFormat;
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

    public static String getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static String DateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
