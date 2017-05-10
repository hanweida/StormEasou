package com.easou.let.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import com.easou.let.pojo.ShowClickLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-8
 * Time: 下午2:31
 * To change this template use File | Settings | File Templates.
 */
public class AspCombinerBolt implements IRichBolt {
    private OutputCollector collector;
    /**
     * The Log.
     */
    Logger Log = LoggerFactory.getLogger(AspCombinerBolt.class);
    /**
     * The Map.
     */
    Map<String, ShowClickLog> map = null;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector outputCollector) {
        //To change body of implemented methods use File | Settings | File Templates.
        System.out.println("AspCombinerBolt： prepare");
        this.collector = outputCollector;
        this.map = new HashMap<String, ShowClickLog>();
    }

    @Override
    public void execute(Tuple input) {
        String key = input.getString(0);
        ShowClickLog showClickLog = (ShowClickLog)input.getValue(1);
        int showNum = 0;
        if(!map.containsKey(key)){
            map.put(key, showClickLog);
        } else {
            ShowClickLog showClickLog1 = map.get(key);
            showNum = showClickLog.getShowNum();
            showClickLog1.setShowNum(showClickLog1.getShowNum()+showNum);
            map.put(key, showClickLog1);
        }
        System.out.println("------------------------------------"+showClickLog.toValue());
        System.out.println("userId:" +showClickLog.getUserId());
        System.out.println("shownum:" +showClickLog.getShowNum());
        collector.ack(input);
    }

    @Override
    public void cleanup() {
        for (Map.Entry<String, ShowClickLog> entry : map.entrySet()) {
            System.out.println("+++++++++++++++++"+entry.getKey() + ": " + entry.getValue().getShowNum());
        }
        map.clear();
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
