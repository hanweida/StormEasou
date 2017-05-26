package com.easou.let.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import com.easou.let.pojo.*;
import com.easou.let.utils.HBaseUtils;
import com.easou.let.utils.PropertiesUtils;
import com.easou.let.utils.SimpleDateUtils;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-16
 * Time: 下午3:21
 * To change this template use File | Settings | File Templates.
 */
public class CdpDBBolt implements IRichBolt {
    private static final Logger LOG = LoggerFactory.getLogger(CdpDBBolt.class);
    //正在持久化总数据
    private boolean cleaning = false;
    private Map<String, ShowClickLog> memcachMap = null;
    private List<ShowClickLog> cachList = null;

    private TaskQueue<ShowClickLog> taskQueue = null;
    private OutputCollector collector;


    /**
     * Wite file.
     */
    public void witeFile(){

    }

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        LOG.info("-----------------------------CdpDBBolt prepare");
        //To change body of implemented methods use File | Settings | File Templates.
        this.collector = collector;
        cleaning = true;
        memcachMap = new HashMap<String, ShowClickLog>();
        cachList = new ArrayList<ShowClickLog>();
        taskQueue = new TaskQueue<ShowClickLog>();

        Timer timer = new Timer();
        timer.schedule(new CdpDBBolt.saveTimer(), new Date(), 3000);
    }

    /**
     * 定时处理缓存数据，在处理数据期间，将clean字段 设置为true。
     */
    class saveTimer extends TimerTask{
        @Override
        public void run() {
            LOG.info("saveTimer Start：----------------------" + SimpleDateUtils.getCurrentTime());
            //To change body of implemented methods use File | Settings | File Templates.
            cleaning = true;
            LOG.info("memcachMapSize：----------------------" + memcachMap.size());
            if(!memcachMap.isEmpty()){
                Iterator iterator = memcachMap.values().iterator();
                //循环 memcachMap 的values，将showClickLog 对象放到task任务列表中
                while (iterator.hasNext()){
                    taskQueue.addItem((ShowClickLog)iterator.next());
                }
                List<Thread> threadList = new ArrayList<Thread>();
                //启动4个线程，存储缓存数据
                for(int i = 0; i< 4 ;i++){
                    Thread thread = new Thread(new Runnable() {
                        public void run() {
                        while (true){
                            ShowClickLog showClickLog = taskQueue.popItem();
                            if(null == showClickLog){
                                taskQueue = new TaskQueue<ShowClickLog>();
                                memcachMap =new HashMap<String, ShowClickLog>();
                                break;
                            } else {
                                if(showClickLog.getUserId() != 8704){
                                    //System.out.println(showClickLog.getCharge());
                                    //LOG.info("---------------------------" + showClickLog.getCharge());
                                    try {
                                        //持久化数据
                                        witeHBase(showClickLog.getCharge());
                                    } catch (IOException e) {
                                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                    }
                                }
                            }
                        }
                        }
                    });
                    threadList.add(thread);
                    thread.start();
                }
                for(Thread thread : threadList){
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
            LOG.info("saveTimer End：----------------------" + SimpleDateUtils.getCurrentTime());
            cleaning = false;
        }
    }

    /**
     * 接收数据
     * @param input
     */
    public void execute(Tuple input) {
        String key = (String)input.getValue(0);
        ShowClickLog showClickLog = (ShowClickLog)input.getValueByField("value");
        memcachData(key, showClickLog, input);
        System.out.println("-------------------------------------" + SimpleDateUtils.getCurrentTime());
    }

    public void cleanup() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 持久化数据
     *
     * @param str the str
     * @throws IOException the io exception
     */
    public void witeData(String str) throws IOException {
        //写在文件中
        witeFile(str);
    }

    /**
     * Wite file.
     *
     * @param str the str
     * @throws IOException the io exception
     */
    public void witeFile(String str) throws IOException {
        PropertiesUtils propertiesUtils = PropertiesUtils.getInstance();
        String cdpDBFilePah = propertiesUtils.getProperties("cdpDBFilePah");
        File file = new File(cdpDBFilePah);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(str);
        fileWriter.flush();
        printWriter.flush();
    }

    /**
     * Wite file.
     *
     * @param str the str
     * @throws IOException the io exception
     */
    public void witeHBase(int str) throws IOException {
        HBaseUtils hBaseUtils = HBaseUtils.getInstance();
        Connection connection = hBaseUtils.getHBaseConn();
        Map<String, Map<String, Long>> familyColumn = new HashMap<String, Map<String, Long>>();
        Map<String, Long> qualifier = new HashMap<String, Long>();
        qualifier.put("show", (long)str);
        //System.out.println((long)str);
        familyColumn.put("info", qualifier);
        hBaseUtils.incDatas(connection, "test", "counts" , familyColumn);
    }

    /**
     * Memqach data.
     * 该方法为将数据整合到内存中 ，将相同的key 合并数据，如果正在数据处理期间（定时器启动时），数据将缓存到cachList中
     *
     * @param key          the key
     * @param showClickLog the show click log
     * @param input        the input
     */
    public void memcachData (String key, ShowClickLog showClickLog, Tuple input) {

        //如果线程正在执行，持久化 总数据中时，不能合并数据，先保存到缓存列表
        if(!cleaning){
            LOG.info("memcachData：------------" + SimpleDateUtils.getCurrentTime());
            ShowClickLog memShowClick = null;
            if(null == memcachMap ){
                memcachMap = new HashMap<String, ShowClickLog>();
            }
            //cachList 不为空，添加 showClickLog
            if(null != cachList){
                cachList.add(showClickLog);
            }
            //cachList 有值，循环累加 数据
            if(null != cachList && cachList.size() > 0){
                for(ShowClickLog showClickLog1 : cachList){
                    if(null == memcachMap.get(key)){
                        memcachMap.put(key, showClickLog1);
                    } else {
                        memShowClick = memcachMap.get(key);
                        memShowClick.setClick(showClickLog1.getClick() + memShowClick.getClick());
                        memShowClick.setCharge(showClickLog1.getCharge() + memShowClick.getCharge());
                        memShowClick.setBidCharge(showClickLog1.getBidCharge() + memShowClick.getBidCharge());
                    }
                }
                //cachList清空
                cachList.clear();
            }
        } else {
            LOG.info("cachData：------------" + SimpleDateUtils.getCurrentTime());
            cachList.add(showClickLog);
        }
        collector.ack(input);
    }


    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    public Map<String, Object> getComponentConfiguration() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
