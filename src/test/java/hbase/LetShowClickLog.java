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

    /**
     * Gets click.
     *
     * @return the click
     */
    public int getClick() {
        return click;
    }

    /**
     * Sets click.
     *
     * @param click the click
     */
    public void setClick(int click) {
        this.click = click;
    }

    /**
     * Gets click type.
     *
     * @return the click type
     */
    public int getClickType() {
        return clickType;
    }

    /**
     * Sets click type.
     *
     * @param clickType the click type
     */
    public void setClickType(int clickType) {
        this.clickType = clickType;
    }

    /**
     * Gets bid charge.
     *
     * @return the bid charge
     */
    public int getBidCharge() {
        return bidCharge;
    }

    /**
     * Sets bid charge.
     *
     * @param bidCharge the bid charge
     */
    public void setBidCharge(int bidCharge) {
        this.bidCharge = bidCharge;
    }

    /**
     * Gets charge.
     *
     * @return the charge
     */
    public int getCharge() {
        return charge;
    }

    /**
     * Sets charge.
     *
     * @param charge the charge
     */
    public void setCharge(int charge) {
        this.charge = charge;
    }

    /**
     * Gets cost date str.
     *
     * @return the cost date str
     */
    public String getCostDateStr() {
        return costDateStr;
    }

    /**
     * Sets cost date str.
     *
     * @param costDateStr the cost date str
     */
    public void setCostDateStr(String costDateStr) {
        this.costDateStr = costDateStr;
    }

    /**
     * Gets dept.
     *
     * @return the dept
     */
    public int getDept() {
        return dept;
    }

    /**
     * Sets dept.
     *
     * @param dept the dept
     */
    public void setDept(int dept) {
        this.dept = dept;
    }

    /**
     * Gets cid.
     *
     * @return the cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * Sets cid.
     *
     * @param cid the cid
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    /**
     * Gets mpay.
     *
     * @return the mpay
     */
    public int getMpay() {
        return mpay;
    }

    /**
     * Sets mpay.
     *
     * @param mpay the mpay
     */
    public void setMpay(int mpay) {
        this.mpay = mpay;
    }

    /**
     * Gets media type.
     *
     * @return the media type
     */
    public int getMediaType() {
        return mediaType;
    }

    /**
     * Sets media type.
     *
     * @param mediaType the media type
     */
    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * Gets charge type.
     *
     * @return the charge type
     */
    public int getChargeType() {
        return chargeType;
    }

    /**
     * Sets charge type.
     *
     * @param chargeType the charge type
     */
    public void setChargeType(int chargeType) {
        this.chargeType = chargeType;
    }

    /**
     * Gets pay type.
     *
     * @return the pay type
     */
    public int getPayType() {
        return payType;
    }

    /**
     * Sets pay type.
     *
     * @param payType the pay type
     */
    public void setPayType(int payType) {
        this.payType = payType;
    }

    /**
     * Gets promotion type.
     *
     * @return the promotion type
     */
    public int getPromotionType() {
        return promotionType;
    }

    /**
     * Sets promotion type.
     *
     * @param promotionType the promotion type
     */
    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }


    /**
     * Gets show num.
     *
     * @return the show num
     */
    public int getShowNum() {
        return showNum;
    }

    /**
     * Sets show num.
     *
     * @param showNum the show num
     */
    public void setShowNum(int showNum) {
        this.showNum = showNum;
    }

    /**
     * Gets wordid.
     *
     * @return the wordid
     */
    public String getWordid() {
        return wordid;
    }

    /**
     * Sets wordid.
     *
     * @param wordid the wordid
     */
    public void setWordid(String wordid) {
        this.wordid = wordid;
    }

    /**
     * Gets winfoid.
     *
     * @return the winfoid
     */
    public String getWinfoid() {
        return winfoid;
    }

    /**
     * Sets winfoid.
     *
     * @param winfoid the winfoid
     */
    public void setWinfoid(String winfoid) {
        this.winfoid = winfoid;
    }

    /**
     * Gets ad type.
     *
     * @return the ad type
     */
    public String getAdType() {
        return adType;
    }

    /**
     * Sets ad type.
     *
     * @param adType the ad type
     */
    public void setAdType(String adType) {
        this.adType = adType;
    }

    /**
     * Gets ad index.
     *
     * @return the ad index
     */
    public String getAdIndex() {
        return adIndex;
    }

    /**
     * Sets ad index.
     *
     * @param adIndex the ad index
     */
    public void setAdIndex(String adIndex) {
        this.adIndex = adIndex;
    }

    /**
     * Gets userid.
     *
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Sets userid.
     *
     * @param userid the userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * Gets planid.
     *
     * @return the planid
     */
    public String getPlanid() {
        return planid;
    }

    /**
     * Sets planid.
     *
     * @param planid the planid
     */
    public void setPlanid(String planid) {
        this.planid = planid;
    }

    /**
     * Gets ideaid.
     *
     * @return the ideaid
     */
    public String getIdeaid() {
        return ideaid;
    }

    /**
     * Sets ideaid.
     *
     * @param ideaid the ideaid
     */
    public void setIdeaid(String ideaid) {
        this.ideaid = ideaid;
    }

    /**
     * Gets unitid.
     *
     * @return the unitid
     */
    public String getUnitid() {
        return unitid;
    }

    /**
     * Sets unitid.
     *
     * @param unitid the unitid
     */
    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }
}
