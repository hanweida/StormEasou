package com.easou.let.fun;

import backtype.storm.topology.FailedException;
import backtype.storm.tuple.Values;
import com.easou.let.pojo.ShowClickLog;
import com.easou.let.utils.SimpleDateUtils;
import org.apache.log4j.Logger;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ES-BF-IT-126 on 2017/7/27.
 */
public class CdpConvertFunction extends BaseFunction {
    private static final Logger logger = Logger.getLogger("operation");
    private SimpleDateFormat ymd_ = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        String line = tuple.getString(0);
        ShowClickLog showClickLog = cdpParge(line);
        if(null != showClickLog){
            //logger.info("ShowClickLog IS_NOT_NULL");
            if(true){
                collector.emit(new Values(showClickLog.toKeyValue(), showClickLog));
            } else {
                throw new FailedException();
            }
        } else {
            //logger.info("ShowClickLog NULL");
            //logger.info("Log "+ line);
        }
    }

    /**
     * Cdp parge show click log.
     * @param line the line
     * @return the show click log
     */
    public ShowClickLog cdpParge(String line){
        String[] array = line.split("\t",10000);
        //默认当前版本长度
        int maxItemCount = 26;
        if(array.length > maxItemCount){
            maxItemCount = array.length;
        }

        if(array.length < maxItemCount){
            return null;
        }

        if(!isParse(array)){
            return null;
        }
        ShowClickLog showClickLog = new ShowClickLog();

        //日志日期
        String str = getShortFormatDate(Long.valueOf(array[21]));
        Date costDate = new Date(Long.valueOf(array[21]));
        String dateStr = SimpleDateUtils.DateToString(costDate);
        if(!"2017-05-25".equals(dateStr)){
            return null;
        }
        showClickLog.setClickType(Integer.valueOf(array[3]));
        showClickLog.setUserId(Integer.valueOf(array[4]));
        showClickLog.setPlanId(Integer.valueOf(array[5]));
        showClickLog.setUnitId(Integer.valueOf(array[6]));
        showClickLog.setBidWordId(Integer.valueOf(array[7]));
        showClickLog.setWordId(Integer.valueOf(array[8]));
        showClickLog.setAdId(Integer.valueOf(array[9]));
        showClickLog.setClick(1);
        showClickLog.setBidCharge(Integer.valueOf(array[10]));
        int charge = (int) Math.rint(Double.valueOf(array[11]));
        if(charge <= 100000){
            showClickLog.setCharge(charge);
        } else {
            System.out.println("消费金额 大于1000");
        }
        showClickLog.setCostDateString(ymd_.format(costDate));
        /**
         * 该版本设置部门属性，用于联盟统计，计算收入分成
         */
        if(maxItemCount >= 30){
            showClickLog.setDept(Integer.parseInt(array[29]));
        }

        /**
         * 该版本设置渠道属性，用于搜索框热词需求时，分析 muid 和mmid
         */
        if(maxItemCount >= 31){
            showClickLog.setCid(array[30]);
        }

        /**
         * 媒体主分成 mpay,mid,mediatype
         */
        if(maxItemCount >= 35){
            showClickLog.setMpay(Integer.parseInt(array[33]));
            showClickLog.setMediaType(Integer.parseInt(array[34]));
        }
        /**
         * 计费类型 idea层级 （1、CPC 2、CPA）
         */
        if(maxItemCount >= 46){
            showClickLog.setChargeType(array[45]);
        }
        return showClickLog;
    }

    /**
     * Is parse boolean.
     *
     * @param values the values
     * @return the boolean
     */
    protected boolean isParse(String[] values) {
        if(values.length>35){
            if("60".equals(values[34])){//media_type物料类型
                //表示设置了该物料类型为锁屏新闻,该类型的物料不会产生charge和点击,因此暂时不考虑计费
                return false;
            }
        }
        return true;
    }

    public static String getShortFormatDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        date.setTime(time);
        return sdf.format(date);
    }
}
