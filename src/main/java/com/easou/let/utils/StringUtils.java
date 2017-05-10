package com.easou.let.utils;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-7
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {

    /**
     * 判断字符串是否为空或者空字符串
     *
     * @param target the target
     * @return boolean
     */
    public static boolean isNullOrEmpty(String target){
        if(null == target || "".equals(target)){
            return true;
        }else {
            return false;
        }
    }
}
