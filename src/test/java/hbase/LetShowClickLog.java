package hbase;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-6-22
 * Time: 下午4:32
 * To change this template use File | Settings | File Templates.
 */
public class LetShowClickLog {

    /**
     * 广告索引
     */
    private String adIndex = "";

    /**
     * 广告类型
     */
    private String adType = "";

    private String userid = "";

    private String planid = "";

    private String ideaid = "";

    private String unitid = "";

    private String wordid = "";

    private String winfoid = "";

    //展示量
    private int showNum;
    //点击量
    private int click;

    //点击类型
    private int clickType;
    //出价金额
    private int bidCharge;
    //消费金额
    private int charge;
    //消费日期字符串
    private String costDateStr  = "";
    //部门
    private int dept;
    //渠道属性
    private String cid  = "";
    //媒体主分成
    private int mpay;
    //物料类型
    private int mediaType;
    //计费类型(idea类型 1、cpc 2、cpa)
    private int chargeType;
    //计费类型 1、cpc 2、cpm
    private int payType;
    //推广类型 1、搜索 2、展示
    private int promotionType;

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getClickType() {
        return clickType;
    }

    public void setClickType(int clickType) {
        this.clickType = clickType;
    }

    public int getBidCharge() {
        return bidCharge;
    }

    public void setBidCharge(int bidCharge) {
        this.bidCharge = bidCharge;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public String getCostDateStr() {
        return costDateStr;
    }

    public void setCostDateStr(String costDateStr) {
        this.costDateStr = costDateStr;
    }

    public int getDept() {
        return dept;
    }

    public void setDept(int dept) {
        this.dept = dept;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getMpay() {
        return mpay;
    }

    public void setMpay(int mpay) {
        this.mpay = mpay;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public int getChargeType() {
        return chargeType;
    }

    public void setChargeType(int chargeType) {
        this.chargeType = chargeType;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }


    public int getShowNum() {
        return showNum;
    }

    public void setShowNum(int showNum) {
        this.showNum = showNum;
    }

    public String getWordid() {
        return wordid;
    }

    public void setWordid(String wordid) {
        this.wordid = wordid;
    }

    public String getWinfoid() {
        return winfoid;
    }

    public void setWinfoid(String winfoid) {
        this.winfoid = winfoid;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getAdIndex() {
        return adIndex;
    }

    public void setAdIndex(String adIndex) {
        this.adIndex = adIndex;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    public String getIdeaid() {
        return ideaid;
    }

    public void setIdeaid(String ideaid) {
        this.ideaid = ideaid;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }
}
