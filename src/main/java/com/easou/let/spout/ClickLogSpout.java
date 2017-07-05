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
import java.io.File;
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
    private String filePath;
    private boolean flag =false;

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //To change body of implemented methods use File | Settings | File Templates.
        declarer.declare(new Fields("line"));
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        //To change body of implemented methods use File | Settings | File Templates.
        this.collector = collector;
        this.filePath = conf.get("cdp.log.file").toString();
        File file = new File(filePath);
//        try {
//            this.fileReader = new FileReader(conf.get("cdp.log.file").toString());
//            this.collector = collector;
//            this.filePath = conf.get("cdp.log.file").toString();
//        } catch (FileNotFoundException e) {
//            logger.error(e.getMessage());
//        }
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
        if(!flag){
            File file = new File(filePath);
            if(file.isDirectory()){
                File[] listFiles = file.listFiles();
                for(File file1 : listFiles){
                    try {
                        FileReader fileReader = new FileReader(file1);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String str = null;
                        try {
                            while (null != (str = bufferedReader.readLine())){
                                //System.out.println(str);
                                collector.emit(new Values(str), str);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                flag = true;
            } else {
                try {
                    this.fileReader = new FileReader(new File(filePath));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String str = null;
                try {
                    while (null != (str = bufferedReader.readLine())){
                        //System.out.println(str);
                        collector.emit(new Values(str), str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }


    }

    public void ack(Object msgId) {

        //System.out.println("------------------------ok ： " + msgId);
    }

    public void fail(Object msgId) {
        //System.out.println("------------------------fail ： " + msgId);
        collector.emit(new Values(msgId));
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
