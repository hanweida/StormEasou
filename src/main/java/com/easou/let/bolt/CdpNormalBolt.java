package com.easou.let.bolt;

import backtype.storm.Constants;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.MessageId;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.easou.let.pojo.ShowClickLog;
import com.easou.let.utils.SimpleDateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-16
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
public class CdpNormalBolt extends BaseBasicBolt{
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("operation");

    private OutputCollector collector;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat ymd_ = new SimpleDateFormat("yyyy-MM-dd");
    int count  = 0;

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        //To change body of implemented methods use File | Settings | File Templates.
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        if(input.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID) && input.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID)){
            System.out.println(SimpleDateUtils.getCurrentTime());
            logger.info("------------------------------------------CdpNormalBolt execute");
        } else {
            String line = input.getString(0);
            logger.info("CdpNormalBolt line num");
            MessageId messageId = input.getMessageId();
            //System.out.println(line);
            ShowClickLog showClickLog = cdpParge(line);
            if(null != showClickLog){
                logger.info("CdpNormalBolt ShowClickLog IS_NOT_NULL");
                collector.emit(new Values(showClickLog.toKeyValue(), showClickLog));
            } else {
                logger.info("CdpNormalBolt ShowClickLog NULL");
                logger.info("CdpNormalBolt Log "+ line);
            }
        }
    }

    public void cleanup() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //To change body of implemented methods use File | Settings | File Templates.
        declarer.declare(new Fields("key", "value"));
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Cdp parge show click log.
     *
     * @param line the line
     * @return the show click log
     */
    public ShowClickLog cdpParge(String line){
        if(StringUtils.isEmpty(line) || "@EE@".equals(line)){
            return null;
        }
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
        //return null;
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

    /**
     * Wite files.
     *
     * @param str the str
     * @throws IOException the io exception
     */
    public static void witeFiles(String str) throws IOException {
        FileWriter fileWriter = new FileWriter("D:\\easouLog\\cdplog\\click_data.9263052", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        System.out.println(str);
        printWriter.println(str);
        fileWriter.flush();
        printWriter.flush();
    }

    public static String getShortFormatDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        date.setTime(time);
        return sdf.format(date);
    }

    public static void main(String[] args) {

    }

}
