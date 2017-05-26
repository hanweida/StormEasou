package com.easou.let.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-16
 * Time: 上午11:01
 * To change this template use File | Settings | File Templates.
 */
public class ClickLogSpout implements IRichSpout {
    private Logger logger = LoggerFactory.getLogger(ClickLogSpout.class);
    private FileReader fileReader;
    private SpoutOutputCollector collector;
    private boolean complete = false;

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //To change body of implemented methods use File | Settings | File Templates.
        declarer.declare(new Fields("line"));
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        //To change body of implemented methods use File | Settings | File Templates.
        try {
            this.fileReader = new FileReader(conf.get("cdp.log.file").toString());
            this.collector = collector;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    public void close() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void activate() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deactivate() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void nextTuple() {
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String str = null;
        try {
            while (null != (str = bufferedReader.readLine())){
                collector.emit(new Values(str), str);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void ack(Object msgId) {

        //System.out.println("------------------------ok ： " + msgId);
    }

    public void fail(Object msgId) {
        System.out.println("------------------------fail ： " + msgId);
        collector.emit(new Values(msgId));
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
