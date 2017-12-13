package com.easou.let.fun;

import com.easou.let.pojo.ShowClickLog;
import org.apache.log4j.Logger;
import storm.trident.operation.CombinerAggregator;
import storm.trident.tuple.TridentTuple;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ES-BF-IT-126 on 2017/7/27.
 */
public class CombinerAgg implements CombinerAggregator<ShowClickLog> {
    private static final Logger logger = Logger.getLogger(CusMemoryMapState.class);

    @Override
    public ShowClickLog init(TridentTuple tuple) {
//        Map<String, ShowClickLog> cachMap = (HashMap<String, ShowClickLog>)tuple.get(0);
//        for(String key : cachMap.keySet()){
//            //logger.info("key=["+key+"] value="+((ShowClickLog)cachMap.get(key)).toValue());
//            //logger.info("key:【"+key+"】"+"Charge:["+((ShowClickLog)val.get(key)).getCharge()+"]");
//            logger.info(((ShowClickLog)cachMap.get(key)).getCharge());
//
//            //logger.info(key);
//        }
        //System.out.println(((ShowClickLog)tuple.get(1)).getCharge());
        return (ShowClickLog)tuple.get(0);
    }

    @Override
    public ShowClickLog combine(ShowClickLog val1, ShowClickLog val2) {
        return val2;
    }

    @Override
    public ShowClickLog zero() {
        return null;
    }
}
