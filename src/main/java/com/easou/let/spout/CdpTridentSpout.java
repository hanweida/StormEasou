package com.easou.let.spout;

import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Fields;
import org.apache.log4j.Logger;
import storm.trident.operation.TridentCollector;
import storm.trident.spout.ITridentSpout;
import storm.trident.topology.TransactionAttempt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ES-BF-IT-126 on 2017/7/26.
 */
public class CdpTridentSpout implements ITridentSpout<String> {
    private static final Logger logger = Logger.getLogger(CdpTridentSpout.class);
    DefaultBatchCoordinator defaultBatchCoordinator = new DefaultBatchCoordinator();
    CdpEmitter cdpEmitter;
    private String filePath;
    private boolean flag =false;

    @Override
    public BatchCoordinator<String> getCoordinator(String txStateId, Map conf, TopologyContext context) {
        return defaultBatchCoordinator;
    }

    @Override
    public Emitter<String> getEmitter(String txStateId, Map conf, TopologyContext context) {
        this.filePath = conf.get("cdp.log.file").toString();
        cdpEmitter = new CdpEmitter(filePath);
        return cdpEmitter;
    }

    @Override
    public Map getComponentConfiguration() {
        return null;
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("cdpTridentFields");
    }

    class DefaultBatchCoordinator implements BatchCoordinator<String>,Serializable{
        @Override
        public String initializeTransaction(long txid, String prevMetadata, String currMetadata) {
            return null;
        }

        @Override
        public void success(long txid) {

        }
        @Override
        public boolean isReady(long txid) {
            return true ;
        }

        @Override
        public void close() {

        }
    }

    class CdpEmitter implements Emitter<String>,Serializable{
        String filePath = null;
        boolean complete = false;
        int i = 0;
        CdpEmitter(String filePath){
            this.filePath = filePath;
        }

        @Override
        public void emitBatch(TransactionAttempt tx, String coordinatorMeta, TridentCollector collector) {
//            if(complete){
//                return;
//            }
            //循环两遍，
            if(i == 2){
                return;
            }
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
                                List<Object> events = new ArrayList<Object>();
                                events.add(str);
                                collector.emit(events);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        complete = true;
                    }
                }
                i++;
            }
        }

        @Override
        public void success(TransactionAttempt tx) {

        }

        @Override
        public void close() {

        }
    }
}
