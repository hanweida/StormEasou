package com.easou.let.fun;

import com.easou.let.pojo.ShowClickLog;
import org.apache.log4j.Logger;
import storm.trident.operation.TridentCollector;
import storm.trident.state.BaseQueryFunction;
import storm.trident.tuple.TridentTuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ES-BF-IT-126 on 2017/7/27.
 */
public class CdpQueryFunction extends BaseQueryFunction<CdpStateQuery, String> {
    private static final Logger logger = Logger.getLogger("operation");
    Map<String, ShowClickLog> totalMap = new HashMap<String, ShowClickLog>();

    @Override
    public List<String> batchRetrieve(CdpStateQuery state, List<TridentTuple> args) {
        List<String> rets = new ArrayList<String>();
        Map<String, ShowClickLog> cachMap = new HashMap<String, ShowClickLog>();
        if(null != args){
            for(TridentTuple input : args){
                if(null != input){
                    cachMap = (HashMap)input.get(0);
                    for(String key : cachMap.keySet()){
                        //logger.info("key=["+key+"] value="+((ShowClickLog)cachMap.get(key)).toValue());
                        //logger.info(((ShowClickLog)cachMap.get(key)).getCharge());
                        //logger.info(key);
                        ShowClickLog showClickLogBak = null;
                        if(null == totalMap.get(key)){
                            totalMap.put(key, (ShowClickLog) cachMap.get(key ));
                        } else {
//                            showClickLogBak = totalMap.get(key);
//                            showClickLogBak.setClick(cachMap.getClick() + showClickLogBak.getClick());
//                            showClickLogBak.setCharge(cachMap.getCharge() + showClickLogBak.getCharge());
//                            showClickLogBak.setBidCharge(cachMap.getBidCharge() + showClickLogBak.getBidCharge());
                        }
                    }
                }
            }
            logger.info("------------------------END-----------------------------");
        }
        return null;
    }

    @Override
    public void execute(TridentTuple tuple, String result, TridentCollector collector) {
        //collector.emit(new Values(result));
    }
}
