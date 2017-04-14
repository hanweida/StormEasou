package com.easou.let.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-4
 * Time: 下午6:03
 * To change this template use File | Settings | File Templates.
 */
public class WordReader implements IRichSpout {

    private FileReader fileReader;
    private SpoutOutputCollector collector;
    private boolean completed = false;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        //To change body of implemented methods use File | Settings | File Templates.
        outputFieldsDeclarer.declare(new Fields("line"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        //To change body of implemented methods use File | Settings | File Templates.
        try {
            this.fileReader = new FileReader(map.get("wordsFile").toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Eroor");
        }
        collector = spoutOutputCollector;
    }

    @Override
    public void close() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void activate() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deactivate() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void nextTuple() {
        if(completed == true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //Do nothing
            }
            return;
        }
        String str;
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try {
            while ((str = bufferedReader.readLine()) != null){
                this.collector.emit(new Values(str), str);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            completed = true;
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void ack(Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
        System.out.println("OK：" + o);
    }

    @Override
    public void fail(Object o) {
        System.out.println("fail：" + o);
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
