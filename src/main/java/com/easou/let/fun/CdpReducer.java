package com.easou.let.fun;

import backtype.storm.topology.FailedException;
import com.easou.let.pojo.ShowClickLog;
import storm.trident.operation.ReducerAggregator;
import storm.trident.tuple.TridentTuple;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ES-BF-IT-126 on 2017/8/7.
 */
public class CdpReducer implements ReducerAggregator<Map<String, ShowClickLog>> {
    @Override
    public Map<String, ShowClickLog> init() {
        return new HashMap<String, ShowClickLog>();
    }

    @Override
    public Map<String, ShowClickLog> reduce(Map<String, ShowClickLog> curr, TridentTuple tuple) {
        String key = tuple.getString(0);
        ShowClickLog tempShowClickLog = (ShowClickLog) tuple.get(1);
        if(curr.containsKey(key)){
            ShowClickLog showClickLog = curr.get(key);
            //System.out.println("TmpCharge："+tempShowClickLog.getUserId()+"----"+tempShowClickLog.getCharge());
            showClickLog.setCharge(showClickLog.getCharge()+tempShowClickLog.getCharge());
            curr.put(key, showClickLog);
        } else {
            curr.put(key, tempShowClickLog);
        }
        return curr;
    }
}
