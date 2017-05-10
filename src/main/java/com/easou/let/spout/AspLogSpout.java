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
 * Asp日志
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-7
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class AspLogSpout implements IRichSpout {
    private FileReader fileReader;
    private SpoutOutputCollector collector;
    private boolean completed = false;

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        try {
            //此处读取日志目录文件
            this.fileReader = new FileReader(map.get("asp.log.file").toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("AspLogSpout open Erro");
        }
        collector = spoutOutputCollector;
    }

    public void nextTuple() {
        //读取本地日志文件
        String str;
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try {
            int i = 0;
            while ((str = bufferedReader.readLine()) != null){
                collector.emit(new Values(str));
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            completed = true;
        }
    }

    public void ack(Object o) {
        //System.out.println("AspLogSpout OK：" + o);
    }

    public void fail(Object o) {
        //System.out.println("AspLogSpout Fail：" + o);
        //System.out.println("Fail：" + o);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("line"));
    }

    
    public Map<String, Object> getComponentConfiguration() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
}
