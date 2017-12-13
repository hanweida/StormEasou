package com.easou.let.fun;

import backtype.storm.tuple.Values;
import com.easou.let.pojo.ShowClickLog;
import org.apache.log4j.Logger;
import storm.trident.operation.BaseAggregator;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ES-BF-IT-126 on 2017/7/27.
 */
public class CdpAgg extends BaseAggregator<Map<String, ShowClickLog>>{
    private static final Logger logger = Logger.getLogger("operation");
    int partition = 0;
    Map<String, ShowClickLog> cachMap = new HashMap<String, ShowClickLog>();



    @Override
    public void prepare(Map conf, TridentOperationContext context) {
        partition = context.getPartitionIndex();
    }

    @Override
    public Map<String, ShowClickLog> init(Object batchId, TridentCollector collector) {
        Map<String, ShowClickLog> initMap = new HashMap<String, ShowClickLog>();
        return initMap;
    }

    @Override
    public void aggregate(Map<String, ShowClickLog> val, TridentTuple tuple, TridentCollector collector) {
        String key = tuple.getString(0);
        ShowClickLog showClickLog = (ShowClickLog)tuple.get(1);
        ShowClickLog showClickLogBak = null;
        if(null == val.get(key)){
            val.put(key, showClickLog);
        } else {
            showClickLogBak = val.get(key);
            showClickLogBak.setClick(showClickLog.getClick() + showClickLogBak.getClick());
            showClickLogBak.setCharge(showClickLog.getCharge() + showClickLogBak.getCharge());
            showClickLogBak.setBidCharge(showClickLog.getBidCharge() + showClickLogBak.getBidCharge());
        }
    }

    @Override
    public void complete(Map<String, ShowClickLog> val, TridentCollector collector) {
//        cachMap = null;
        for(String key : val.keySet()){
            //logger.info("key=["+key+"] value="+((ShowClickLog)cachMap.get(key)).toValue());
            //logger.info("key:【"+key+"】"+"Charge:["+((ShowClickLog)val.get(key)).getCharge()+"]");
            //logger.info(((ShowClickLog)val.get(key)).getCharge());

            //logger.info(key);
        }
        //logger.info("------------------------END-----------------------------");
        collector.emit(new Values(val));
    }
}
