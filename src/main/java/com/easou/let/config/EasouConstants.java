package com.easou.let.config;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-7
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class EasouConstants {


    /**
     * The constant ASP_FLAG.
     */
    public static final String ASP_FLAG = " ad=";
    /**
     * The constant ASP_FLAG2.
     */
    public static final String ASP_FLAG2 = "] show_ver=";
    /**
     * The constant ASP_FLAG3.
     */
    public static final String ASP_FLAG3 = " kdad=";

    /**
     * The constant MUL_AD_FlAG.
     */
    public static final String MUL_AD_FlAG = "|";
    /**
     * The constant LOG_AD_DELIMITER.
     */
    public static final String LOG_AD_DELIMITER = "\\|";
    /**
     * The constant AD_PROPERTY_DELIMITER.
     */
    public static final String AD_PROPERTY_DELIMITER = "\t";

    /**
     * The constant PAY_TYPE_CPC.
     */
//付费类型:1 CPC付费 2 CPM付费
    public static final int PAY_TYPE_CPC = 1;
    /**
     * The constant PAY_TYPE_CPM.
     */
    public static final int PAY_TYPE_CPM = 2;


    /**
     * The constant PROMOTION_TYPE_SEARCH.
     */
//推广类型1：搜索推广 2：展示推广
    public static final int PROMOTION_TYPE_SEARCH = 1;
    /**
     * The constant PROMOTION_TYPE_DISPLAY.
     */
    public static final int PROMOTION_TYPE_DISPLAY = 2;

    /**
     * The constant userIdBit.
     */
//存入HBase数据位数
    public static final int userIdBit = 5;
    /**
     * The constant planIdBit.
     */
    public static final int planIdBit = 7;
    /**
     * The constant unitIdBit.
     */
    public static final int unitIdBit = 8;
    /**
     * The constant wordIdBit.
     */
    public static final int wordIdBit = 9;
    /**
     * The constant adIdBit.
     */
    public static final int adIdBit = 8;
    /**
     * The constant bidWordIdBit.
     */
    public static final int bidWordIdBit = 9;
    /**
     * The constant clickTypeBit.
     */
    public static final int clickTypeBit = 2;
    /**
     * The constant promotionTypeBit.
     */
    public static final int promotionTypeBit = 1;
    /**
     * The constant payType.
     */
    public static final int payType = 1;

    /**
     * The constant HBASE_SHOWNUM.
     */
//HBase中的字段 quliafiy
    //展现
    public static final String HBASE_SHOWNUM = "shownum";
    /**
     * The constant HBASE_CLICK.
     */
//点击
    public static final String HBASE_CLICK = "click";
    /**
     * The constant HBASE_CHARGE.
     */
//charge
    public static final String HBASE_CHARGE = "charge";
    /**
     * The constant HBASE_BIDCHARGE.
     */
//bidcharge
    public static final String HBASE_BIDCHARGE = "bidcharge";
    /**
     * The constant HBASE_STATE.
     */
//state
    public static final String HBASE_STATE = "state";

}
