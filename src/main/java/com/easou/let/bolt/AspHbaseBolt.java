package com.easou.let.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import com.easou.let.config.EasouConstants;
import com.easou.let.dao.HBaseDao;
import com.easou.let.pojo.ShowClickLog;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Asp日志Normal 存入hbase 中
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-10
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 */
public class AspHbaseBolt extends BaseBasicBolt {
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        doSumData(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Do sum data boolean.
     *
     * @param input the input
     * @return the boolean
     */
    public boolean doSumData(Tuple input){
        System.out.println("Thread.currentThread()："+Thread.currentThread());
        ShowClickLog showClickLog = (ShowClickLog)input.getValue(1);
        HBaseDao hBaseDao = HBaseDao.getInstance();
        String rowKey = showClickLog.toKey();
        try {
            String showNumValue = Bytes.toString(hBaseDao.selectDatas("t_showclicklog", rowKey, "info".getBytes(), EasouConstants.HBASE_SHOWNUM.getBytes()));
            String clickValue =  Bytes.toString(hBaseDao.selectDatas("t_showclicklog", rowKey, "info".getBytes(), EasouConstants.HBASE_CLICK.getBytes()));
            String chargeValue =  Bytes.toString(hBaseDao.selectDatas("t_showclicklog", rowKey, "info".getBytes(), EasouConstants.HBASE_CHARGE.getBytes()));
            String bidChargeValue =  Bytes.toString(hBaseDao.selectDatas("t_showclicklog", rowKey, "info".getBytes(), EasouConstants.HBASE_BIDCHARGE.getBytes()));
            int showNumInt = 0;
            if(StringUtils.isNotEmpty(showNumValue)){
                showNumInt = Integer.parseInt(clickValue);
            }
            int clickValueInt = 0;
            if(StringUtils.isNotEmpty(clickValue)){
                clickValueInt = Integer.parseInt(clickValue);
            }
            int chargeValueInt = 0;
            if(StringUtils.isNotEmpty(chargeValue)){
                chargeValueInt = Integer.parseInt(chargeValue);
            }
            int bidChargeValueInt = 0;
            if(StringUtils.isNotEmpty(bidChargeValue)){
                bidChargeValueInt = Integer.parseInt(bidChargeValue);
            }
            hBaseDao.putDatas("t_showclicklog", rowKey, String.valueOf(showClickLog.getShowNum()  + showNumInt), "info".getBytes(), EasouConstants.HBASE_SHOWNUM.getBytes());
            hBaseDao.putDatas("t_showclicklog", rowKey, String.valueOf(showClickLog.getClick()  + clickValueInt), "info".getBytes(), EasouConstants.HBASE_CLICK.getBytes());
            hBaseDao.putDatas("t_showclicklog", rowKey, String.valueOf(showClickLog.getCharge()  + chargeValueInt), "info".getBytes(), EasouConstants.HBASE_CHARGE.getBytes());
            hBaseDao.putDatas("t_showclicklog", rowKey, String.valueOf(showClickLog.getBidCharge()  + bidChargeValueInt), "info".getBytes(), EasouConstants.HBASE_BIDCHARGE.getBytes());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
            return true;
    }
}
