package com.easou.let.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import com.easou.let.pojo.*;
import com.easou.let.utils.HBaseUtils;
import com.easou.let.utils.PropertiesUtils;
import com.easou.let.utils.SimpleDateUtils;
import com.easou.let.utils.lockzk.DoTemplate;
import com.easou.let.utils.lockzk.LockService;
import org.apache.hadoop.hbase.client.Connection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-16
 * Time: 下午3:21
 * To change this template use File | Settings | File Templates.
 */
public class CdpDBBolt extends BaseBasicBolt{
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger("operation");

    private AtomicBoolean cleaning = new AtomicBoolean(false);
    private HashMap<String, ShowClickLog> memcachMap = null;
    private OutputCollector collector;
    private HashMap<String, ShowClickLog> dataMaps = new HashMap<String, ShowClickLog>();



    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        //LOG.info("-----------------------------CdpDBBolt prepare");
        //To change body of implemented methods use File | Settings | File Templates.
        this.collector = collector;
        memcachMap = new HashMap<String, ShowClickLog>();
        Timer timer = new Timer();
        timer.schedule(new CdpDBBolt.saveTimer(), new Date(), 20000);
    }

    /**
     * 定时处理缓存数据，在处理数据期间，将clean字段 设置为true。
     */
    class saveTimer extends TimerTask{
        @Override
        public void run() {
            LOG.info("saveTimer Start：----------------------" + SimpleDateUtils.getCurrentTime());
            synchronized (cleaning) {
                cleaning.getAndSet(true);
                dataMaps = (HashMap<String, ShowClickLog>) memcachMap.clone();
                memcachMap.clear();
                cleaning.getAndSet(false);
            }
            if(null != dataMaps && dataMaps.size() > 0){
                for(Map.Entry<String, ShowClickLog> entry : dataMaps.entrySet()){
                    LOG.info("Value: " + entry.getValue().getCharge());
                    //持久化数据
                    witeHBase(entry.getValue().getCharge());
                }
                dataMaps.clear();
            }
        }
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
        ShowClickLog memShowClick = null;
        synchronized (cleaning){
            if(!cleaning.get()){
                if(null == memcachMap ){
                    memcachMap = new HashMap<String, ShowClickLog>();
                }
                if(null == memcachMap.get(key)){
                    memcachMap.put(key, showClickLog);
                } else {
                    memShowClick = memcachMap.get(key);
                    memShowClick.setClick(showClickLog.getClick() + memShowClick.getClick());
                    memShowClick.setCharge(showClickLog.getCharge() + memShowClick.getCharge());
                    memShowClick.setBidCharge(showClickLog.getBidCharge() + memShowClick.getBidCharge());
                }
            }
        }
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String key = (String)input.getValue(0);
        ShowClickLog showClickLog = (ShowClickLog)input.getValueByField("value");
        LOG.info("CdpDBBolt execute");
        memcachData(key, showClickLog, input);
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
    public void witeHBase(final int str) {
            new LockService().doService(new DoTemplate() {
                Connection connection = null;
                @Override
                public void excute() {
                    HBaseUtils hBaseUtils = HBaseUtils.getInstance();

                    try {
                        connection = hBaseUtils.getHBaseConn();
                        Map<String, Map<String, Long>> familyColumn = new HashMap<String, Map<String, Long>>();
                        Map<String, Long> qualifier = new HashMap<String, Long>();
                        qualifier.put("show", (long)str);
                        familyColumn.put("info", qualifier);
                        hBaseUtils.incDatas(connection, "test", "counts" , familyColumn);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            connection.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    public Map<String, Object> getComponentConfiguration() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
