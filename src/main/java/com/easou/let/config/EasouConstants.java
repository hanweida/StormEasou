package com.easou.let.config;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-7
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class EasouConstants {


    public static final String ASP_FLAG = " ad=";
    public static final String ASP_FLAG2 = "] show_ver=";
    public static final String ASP_FLAG3 = " kdad=";

    public static final String MUL_AD_FlAG = "|";
    public static final String LOG_AD_DELIMITER = "\\|";
    public static final String AD_PROPERTY_DELIMITER = "\t";

    //付费类型:1 CPC付费 2 CPM付费
    public static final int PAY_TYPE_CPC = 1;
    public static final int PAY_TYPE_CPM = 2;


    //推广类型1：搜索推广 2：展示推广
    public static final int PROMOTION_TYPE_SEARCH = 1;
    public static final int PROMOTION_TYPE_DISPLAY = 2;

    //存入HBase数据位数
    public static final int userIdBit = 5;
    public static final int planIdBit = 7;
    public static final int unitIdBit = 8;
    public static final int wordIdBit = 9;
    public static final int adIdBit = 8;
    public static final int bidWordIdBit = 9;
    public static final int clickTypeBit = 2;
    public static final int promotionTypeBit = 1;
    public static final int payType = 1;

    //HBase中的字段 quliafiy
    //展现
    public static final String HBASE_SHOWNUM = "shownum";
    //点击
    public static final String HBASE_CLICK = "click";
    //charge
    public static final String HBASE_CHARGE = "charge";
    //bidcharge
    public static final String HBASE_BIDCHARGE = "bidcharge";
    //state
    public static final String HBASE_STATE = "state";

}
