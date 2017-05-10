package com.easou.let.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.easou.let.config.EasouConstants;
import com.easou.let.pojo.ShowClickLog;
import com.easou.let.utils.FileUtils;
import com.easou.let.utils.SimpleDateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-7
 * Time: 下午3:21
 * To change this template use File | Settings | File Templates.
 */
public class AspNormalBolt implements IRichBolt {
    private Logger Log = LoggerFactory.getLogger(AspNormalBolt.class);
    private OutputCollector collector;
    /**
     * The Ymd.
     */
    protected SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    /**
     * The Now date.
     */
    Calendar nowDate = Calendar.getInstance();
    /**
     * The constant i.
     */
//nowDate.add(Calendar.HOUR_OF_DAY, -1);
    //nowDate.add(Calendar.HOUR_OF_DAY, -18);
    public static int i = 0;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    public void execute(Tuple tuple) {
        //获得asp 一条数据
        String line = tuple.getString(0);
        Date date = new Date();

        //解析数据
        List<ShowClickLog> list = aspParse(line, null);
        if(null != list){
            for(ShowClickLog showClickLog : list){
                collector.emit(tuple, new Values(showClickLog.toKeyValue(), showClickLog));
            }
        }
        collector.ack(tuple);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Asp parse list.
     *
     * @param line the line
     * @param date the date
     * @return the list
     */
    public List<ShowClickLog> aspParse(String line, String date){
        //验证日志数据是否满足,不满足返回空
        if(!FileUtils.validateAspLog(line)){
            return null;
        }
        String ads = "";
        List<ShowClickLog> list = null;

        //如果包含 ] show_ver= 说明日志合格
        if(line.contains(EasouConstants.ASP_FLAG2)){
            int showVersion = FileUtils.lineAnalise2Int(line, EasouConstants.ASP_FLAG2);
            //包含kdad 或者ad 段
            if(null != line && (line.contains(EasouConstants.ASP_FLAG) || line.contains(EasouConstants.ASP_FLAG3))){
                list = new ArrayList<ShowClickLog>();
                if(line.contains(EasouConstants.ASP_FLAG)){
                    ads = line.substring(line.indexOf(EasouConstants.ASP_FLAG) + EasouConstants.ASP_FLAG.length(), line.length());
                }
                //如果包含 kdad 段，截取kdad段
                if(line.contains(EasouConstants.ASP_FLAG3)){
                    if(StringUtils.isNotEmpty(ads)){
                        ads += EasouConstants.MUL_AD_FlAG;
                    }
                    ads += FileUtils.lineAnalise(line, EasouConstants.ASP_FLAG3);
                }
                //获得当前日期
                //String dateStr = ymd.format(date);
                int currentYear = SimpleDateUtils.getCurrentYear();
                date = currentYear+"-" + line.substring(0,40).split(" ")[1];
                //if(){}
                //date = year+line.substring(0,40).split(" ")[1];
                int maxItemCount = 0;
                List<String> adStrGoodList = new ArrayList<String>();
                List<String> adStrList = new ArrayList<String>();
                //如果包含多条广告 用"|"分割
                if(ads.contains(EasouConstants.MUL_AD_FlAG)){
                    String [] aspValues = ads.split(EasouConstants.LOG_AD_DELIMITER);
                    /*按 /t 分隔符 分割，获得*/
                    for(String aspValue : aspValues){
                        String[] adPropertyValueArray = aspValue.split(EasouConstants.AD_PROPERTY_DELIMITER);
                        if(adPropertyValueArray.length > maxItemCount){
                            maxItemCount = adPropertyValueArray.length;
                        }
                    }

                    //获得所有的日志数组，放到list中
                    for(String aspValue : aspValues){
                        String[] adPropertyValueArray = aspValue.split(EasouConstants.AD_PROPERTY_DELIMITER);
                        if(adPropertyValueArray.length >= maxItemCount){
                            adStrList.add(aspValue);
                        }
                    }

                    //将有效日志数组添加adStrGoodList
                    for(String ad : adStrList){
                        if(null != ad && !"".equals(ad)){
                            adStrGoodList.add(ad);
                        }
                    }

                    //将日志实例化
                    for(String ad : adStrGoodList){
                        if(null != ad &&!"".equals(ad)){
                            ShowClickLog showClickLog = setValuesToObj(ad.split(EasouConstants.AD_PROPERTY_DELIMITER), ad, date, maxItemCount);
                            list.add(showClickLog);
                        }
                    }
                } else {
                    //如果单条广告
                    ShowClickLog showClickLog = setValuesToObj(ads.split(EasouConstants.AD_PROPERTY_DELIMITER), ads, date, maxItemCount);
                    list.add(showClickLog);
                }

            } else {
                Log.info("asp log not exists");
            }
        } else {
            return null;
        }
        return list;
    }

    /**
     * parse数据到对象中
     */
    private ShowClickLog setValuesToObj(String[] aspValues,String ad,String date,int maxItemCount){
        try{
            ShowClickLog scl = new ShowClickLog();
            String[] array = ad.split(EasouConstants.AD_PROPERTY_DELIMITER, 100);
            maxItemCount = array.length;

            if(array!=null){//保证数据是完整的
                scl.setUserId(Integer.valueOf(array[2]));//用户id
                scl.setPlanId(Integer.valueOf(array[3]));//计划id
                scl.setUnitId(Integer.valueOf(array[4]));//广告组id
                scl.setWordId(Integer.valueOf(array[6]));//关键词id
                scl.setBidWordId(Integer.valueOf(array[5]));//拍卖词id
                scl.setAdId(Integer.valueOf(array[7]));//创意id
                scl.setShowNum(1);//本次统计展示次数
                scl.setClickType(0);//1:点击，2：拨打，默认是0
                scl.setBidCharge(0);//默认是0
                scl.setClick(0);//默认是0
                scl.setCharge(0);//默认是0
                scl.setDown(0);//默认是0
                scl.setActive(0);//默认是0

                //Date costdate = ymd.parse(date);
                //scl.setCostDate(costdate);//消费日期
                scl.setCostDateString(date);

                if(maxItemCount>23){
                    scl.setPayType(Integer.valueOf(array[22]));//计费类型1、cpc；2、cpm
                    scl.setPromotionType(Integer.valueOf(array[23]));//推广类型1、搜索；2、展示
                    if(scl.getPayType()==EasouConstants.PAY_TYPE_CPM && scl.getPromotionType()==EasouConstants.PROMOTION_TYPE_DISPLAY){//展示广告CPM的show在CDP日志中提取
                        scl.setShowNum(0);
                    }
                }else{
                    scl.setPayType(EasouConstants.PAY_TYPE_CPC);//默认
                    scl.setPromotionType(EasouConstants.PROMOTION_TYPE_SEARCH);//默认
                }
            }
            return scl;
        }catch(Exception ex){
            return null;
        }
    }
    public void cleanup() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("key", "showclicklog"));
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
