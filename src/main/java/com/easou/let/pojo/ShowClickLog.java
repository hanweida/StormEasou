package com.easou.let.pojo;

import com.easou.let.config.EasouConstants;

import java.util.Date;

/**
 * 展现日志/点击日志数据实体
 */
public class ShowClickLog {
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCostDate() {
		return costDate;
	}
	public void setCostDate(Date costDate) {
		this.costDate = costDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public int getWordId() {
		return wordId;
	}
	public void setWordId(int wordId) {
		this.wordId = wordId;
	}
	public int getAdId() {
		return adId;
	}
	public void setAdId(int adId) {
		this.adId = adId;
	}
	public int getShowNum() {
		return showNum;
	}
	public void setShowNum(int showNum) {
		this.showNum = showNum;
	}
	public int getClick() {
		return click;
	}
	public void setClick(int click) {
		this.click = click;
	}
	public int getCharge() {
		return charge;
	}
	public void setCharge(int charge) {
		this.charge = charge;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getDayPart() {
		return dayPart;
	}
	public void setDayPart(int dayPart) {
		this.dayPart = dayPart;
	}
	public int getRegion() {
		return region;
	}
	public void setRegion(int region) {
		this.region = region;
	}
	public Date getSaveTime() {
		return saveTime;
	}
	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}
	public int getBidWordId() {
		return bidWordId;
	}
	public void setBidWordId(int bidWordId) {
		this.bidWordId = bidWordId;
	}

    public int getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

	public String getCostDateString() {
		return costDateString;
	}

	public void setCostDateString(String costDateString) {
		this.costDateString = costDateString;
	}

	public String getSaveTimeString() {
		return saveTimeString;
	}

	public void setSaveTimeString(String saveTimeString) {
		this.saveTimeString = saveTimeString;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getUnionUserId() {
		return unionUserId;
	}

	public void setUnionUserId(int unionUserId) {
		this.unionUserId = unionUserId;
	}

	public int getMediaId() {
		return mediaId;
	}

	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}

	public int getAppShowType() {
		return appShowType;
	}

	public void setAppShowType(int appShowType) {
		this.appShowType = appShowType;
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

	public int getMediaType() {
		return mediaType;
	}

	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getEntry() {
		return entry;
	}

	public void setEntry(int entry) {
		this.entry = entry;
	}

	public int getMpay() {
		return mpay;
	}

	public void setMpay(int mpay) {
		this.mpay = mpay;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

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
