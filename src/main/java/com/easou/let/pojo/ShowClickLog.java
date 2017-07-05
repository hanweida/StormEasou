package com.easou.let.pojo;

import com.easou.let.config.EasouConstants;

import java.io.Serializable;
import java.util.Date;

/**
 * 展现日志/点击日志数据实体
 */
public class ShowClickLog implements Serializable{
	private int	id	;//	自增id	
	private Date costDate	;//	消费日期	
	private int	userId	;//	用户id	
	private int	planId	;//	相关计划id	
	private int	unitId	;//	相关广告组id	
	private int	wordId	;//	相关关键词id	
	private int	adId	;//	相关广告id	
	private int	showNum	;//	展现次数	
	private int	click	;//	点击次数	
	private int	charge	;//	消费额	
	private int	state	;//	状态	
	private int	dayPart	;//	时段	
	private int	region	;//	地域	
	private Date saveTime	;//	保存时间	
	private int bidWordId; //拍卖词id
	private int bidCharge;	//出价总额
	private int clickType;	//点击类型
    private int payType;//计费类型1、cpc；2、cpm
    private int promotionType;//推广类型1：搜索推广 2：展示推广，请注意：asp,cdp对应此字段的key为：spread_type,不是prom_type

    private String costDateString;//消费日期 字符串格式
    private String saveTimeString;//保存时间	字符串格式
    private int version;//日志版本,mis系统版本号为1；广告联盟版本为2；
    private int unionUserId;//联盟系统中userID
    private int mediaId;//媒体ID
    private int appShowType;//广告类型。0=UNKNOWN；1=横幅广告 2=全屏广告  3=文本广告  4=推送广告  5=插屏广告
    private int dept;//部门
    private int mediaType;//物料类型
    private int down;//积分墙下载次数
    private int active;//积分墙激活次数
    private int entry;//入口量次数
    private int mpay;//媒体主分成
    private String chargeType = "";//计费类型 idea层级(1、CPC，2、CPA)，用于锁屏广告区分积分墙还是正常广告使用
    private String cid = "";//渠道id,20131216日,添加搜索框热词时增加字段

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
	 * Gets id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets cost date.
	 *
	 * @return the cost date
	 */
	public Date getCostDate() {
		return costDate;
	}

	/**
	 * Sets cost date.
	 *
	 * @param costDate the cost date
	 */
	public void setCostDate(Date costDate) {
		this.costDate = costDate;
	}

	/**
	 * Gets user id.
	 *
	 * @return the user id
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Sets user id.
	 *
	 * @param userId the user id
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Gets plan id.
	 *
	 * @return the plan id
	 */
	public int getPlanId() {
		return planId;
	}

	/**
	 * Sets plan id.
	 *
	 * @param planId the plan id
	 */
	public void setPlanId(int planId) {
		this.planId = planId;
	}

	/**
	 * Gets unit id.
	 *
	 * @return the unit id
	 */
	public int getUnitId() {
		return unitId;
	}

	/**
	 * Sets unit id.
	 *
	 * @param unitId the unit id
	 */
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	/**
	 * Gets word id.
	 *
	 * @return the word id
	 */
	public int getWordId() {
		return wordId;
	}

	/**
	 * Sets word id.
	 *
	 * @param wordId the word id
	 */
	public void setWordId(int wordId) {
		this.wordId = wordId;
	}

	/**
	 * Gets ad id.
	 *
	 * @return the ad id
	 */
	public int getAdId() {
		return adId;
	}

	/**
	 * Sets ad id.
	 *
	 * @param adId the ad id
	 */
	public void setAdId(int adId) {
		this.adId = adId;
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
	 * Gets state.
	 *
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * Sets state.
	 *
	 * @param state the state
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * Gets day part.
	 *
	 * @return the day part
	 */
	public int getDayPart() {
		return dayPart;
	}

	/**
	 * Sets day part.
	 *
	 * @param dayPart the day part
	 */
	public void setDayPart(int dayPart) {
		this.dayPart = dayPart;
	}

	/**
	 * Gets region.
	 *
	 * @return the region
	 */
	public int getRegion() {
		return region;
	}

	/**
	 * Sets region.
	 *
	 * @param region the region
	 */
	public void setRegion(int region) {
		this.region = region;
	}

	/**
	 * Gets save time.
	 *
	 * @return the save time
	 */
	public Date getSaveTime() {
		return saveTime;
	}

	/**
	 * Sets save time.
	 *
	 * @param saveTime the save time
	 */
	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}

	/**
	 * Gets bid word id.
	 *
	 * @return the bid word id
	 */
	public int getBidWordId() {
		return bidWordId;
	}

	/**
	 * Sets bid word id.
	 *
	 * @param bidWordId the bid word id
	 */
	public void setBidWordId(int bidWordId) {
		this.bidWordId = bidWordId;
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
	 * Gets cost date string.
	 *
	 * @return the cost date string
	 */
	public String getCostDateString() {
		return costDateString;
	}

	/**
	 * Sets cost date string.
	 *
	 * @param costDateString the cost date string
	 */
	public void setCostDateString(String costDateString) {
		this.costDateString = costDateString;
	}

	/**
	 * Gets save time string.
	 *
	 * @return the save time string
	 */
	public String getSaveTimeString() {
		return saveTimeString;
	}

	/**
	 * Sets save time string.
	 *
	 * @param saveTimeString the save time string
	 */
	public void setSaveTimeString(String saveTimeString) {
		this.saveTimeString = saveTimeString;
	}

	/**
	 * Gets version.
	 *
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Sets version.
	 *
	 * @param version the version
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * Gets union user id.
	 *
	 * @return the union user id
	 */
	public int getUnionUserId() {
		return unionUserId;
	}

	/**
	 * Sets union user id.
	 *
	 * @param unionUserId the union user id
	 */
	public void setUnionUserId(int unionUserId) {
		this.unionUserId = unionUserId;
	}

	/**
	 * Gets media id.
	 *
	 * @return the media id
	 */
	public int getMediaId() {
		return mediaId;
	}

	/**
	 * Sets media id.
	 *
	 * @param mediaId the media id
	 */
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}

	/**
	 * Gets app show type.
	 *
	 * @return the app show type
	 */
	public int getAppShowType() {
		return appShowType;
	}

	/**
	 * Sets app show type.
	 *
	 * @param appShowType the app show type
	 */
	public void setAppShowType(int appShowType) {
		this.appShowType = appShowType;
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
	 * Gets down.
	 *
	 * @return the down
	 */
	public int getDown() {
		return down;
	}

	/**
	 * Sets down.
	 *
	 * @param down the down
	 */
	public void setDown(int down) {
		this.down = down;
	}

	/**
	 * Gets active.
	 *
	 * @return the active
	 */
	public int getActive() {
		return active;
	}

	/**
	 * Sets active.
	 *
	 * @param active the active
	 */
	public void setActive(int active) {
		this.active = active;
	}

	/**
	 * Gets entry.
	 *
	 * @return the entry
	 */
	public int getEntry() {
		return entry;
	}

	/**
	 * Sets entry.
	 *
	 * @param entry the entry
	 */
	public void setEntry(int entry) {
		this.entry = entry;
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
	 * Gets charge type.
	 *
	 * @return the charge type
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * Sets charge type.
	 *
	 * @param chargeType the charge type
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * To key value string.
	 *
	 * @return the string
	 */
	public String toKeyValue(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getUserId());
        stringBuilder.append("_");
        stringBuilder.append(this.getPlanId());
        stringBuilder.append("_");
        stringBuilder.append(this.getUnitId());
        stringBuilder.append("_");
        stringBuilder.append(this.getWordId());
        stringBuilder.append("_");
        stringBuilder.append(this.getAdId());
        stringBuilder.append("_");
        stringBuilder.append(this.getBidWordId());
        stringBuilder.append("_");
        stringBuilder.append(this.getClickType());
        stringBuilder.append("_");
        stringBuilder.append(this.getCostDateString());
        stringBuilder.append("_");
        stringBuilder.append(this.getPayType());
        stringBuilder.append("_");
        stringBuilder.append(this.getPromotionType());
        return stringBuilder.toString();
    }

	/**
	 * To value string.
	 *
	 * @return the string
	 */
	public String toValue(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getUserId());
        stringBuilder.append("_");
        stringBuilder.append(this.getPlanId());
        stringBuilder.append("_");
        stringBuilder.append(this.getUnitId());
        stringBuilder.append("_");
        stringBuilder.append(this.getWordId());
        stringBuilder.append("_");
        stringBuilder.append(this.getAdId());
        stringBuilder.append("_");
        stringBuilder.append(this.getBidWordId());
        stringBuilder.append("_");
        stringBuilder.append(this.getClickType());
        stringBuilder.append("_");
        stringBuilder.append(this.getCostDateString());
        stringBuilder.append("_");
        stringBuilder.append(this.getPayType());
        stringBuilder.append("_");
        stringBuilder.append(this.getPromotionType());
        stringBuilder.append("_");
        stringBuilder.append(this.getShowNum());
        stringBuilder.append("_");
        stringBuilder.append(this.getClick());
        stringBuilder.append("_");
        stringBuilder.append(this.getCharge());
        stringBuilder.append("_");
        stringBuilder.append(this.getState());
        stringBuilder.append("_");
        stringBuilder.append(this.getBidCharge());
        return stringBuilder.toString();
    }

	/**
	 * To key string.
	 *
	 * @return the string
	 */
	public String toKey(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getCostDateString());
        stringBuilder.append(this.getAddedZeroStr(String.valueOf(this.getUserId()), EasouConstants.userIdBit));
        stringBuilder.append(this.getAddedZeroStr(String.valueOf(this.getPlanId()), EasouConstants.planIdBit));
        stringBuilder.append(this.getAddedZeroStr(String.valueOf(this.getUnitId()), EasouConstants.unitIdBit));
        stringBuilder.append(this.getAddedZeroStr(String.valueOf(this.getWordId()), EasouConstants.wordIdBit));
        stringBuilder.append(this.getAddedZeroStr(String.valueOf(this.getAdId()), EasouConstants.adIdBit));
        stringBuilder.append(this.getAddedZeroStr(String.valueOf(this.getBidWordId()), EasouConstants.bidWordIdBit));
        stringBuilder.append(this.getAddedZeroStr(String.valueOf(this.getClickType()), EasouConstants.clickTypeBit));
        stringBuilder.append(this.getAddedZeroStr(String.valueOf(this.getPayType()), EasouConstants.payType));
        stringBuilder.append(this.getAddedZeroStr(String.valueOf(this.getBidWordId()), EasouConstants.bidWordIdBit));
        stringBuilder.append(this.getAddedZeroStr(String.valueOf(this.getPromotionType()), EasouConstants.promotionTypeBit));
        return stringBuilder.toString();
    }


	/**
	 * Get added zero str string.
	 *
	 * @param str the str
	 * @param bit the bit
	 * @return the string
	 */
	public String getAddedZeroStr(String str, int bit){
        int zeroLengh = bit - str.length();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i <= zeroLengh; i++){
            stringBuilder.append("0");
        }
        stringBuilder.append(str);
        return stringBuilder.toString();
    }
}
