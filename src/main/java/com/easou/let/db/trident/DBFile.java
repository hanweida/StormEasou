package com.easou.let.db.trident;

import backtype.storm.topology.FailedException;
import com.easou.let.pojo.ShowClickLog;
import com.easou.let.utils.HBaseUtils;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.log4j.Logger;
import storm.trident.state.TransactionalValue;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ES-BF-IT-126 on 2017/8/8.
 */
public class DBFile<T> {
    private static final Logger logger = Logger.getLogger("operation");
    int i =0;
    public void writer(List<Object> key, T val) throws IOException{
        FileWriter fileWriter = null;
        fileWriter = new FileWriter("D:\\easouLog\\cdplog\\click_data.result", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        HashMap<String, ShowClickLog> sclMap = ((HashMap<String, ShowClickLog>)(((TransactionalValue)val).getVal()));
        for(Map.Entry<String, ShowClickLog> entry : sclMap.entrySet()){
            //logger.info("USER ID："+entry.getValue().getCharge());
            if(entry.getValue().getUserId() == 10030){
                logger.info("USER ID："+entry.getValue().getUserId());
                logger.info("USER ID："+entry.getValue().getUserId());
                throw new RuntimeException();
                //throw new IOException("dddd");

                //如果抛出FailedException 则会重新发送失败 数据源
                //throw new FailedException();
            }

            //写到HBase中
            //witeHBase(entry.getValue());
//                if(entry.getValue().getUserId() == 10030 && i == 0){
//                    i++;
//                    logger.info("FailedException USER ID："+entry.getValue().getUserId());
//                    logger.info("FailedException USER Charge："+entry.getValue().getCharge());
//                    throw new FailedException();
//                }
            //写到文件中
            printWriter.println(entry.getValue().getCharge());
            fileWriter.flush();
            printWriter.flush();
        }
    }

    public void witeHBase(final int str) {
        Connection connection = null;
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

    public void witeHBase(final ShowClickLog showClickLog) throws IOException {
        Connection connection = null;
        HBaseUtils hBaseUtils = HBaseUtils.getInstance();
        connection = hBaseUtils.getHBaseConn();
        Map<String, Map<String, Long>> familyColumn = new HashMap<String, Map<String, Long>>();
        Map<String, Long> qualifier = new HashMap<String, Long>();
        qualifier.put("click", (long)showClickLog.getCharge());
        familyColumn.put("info", qualifier);
        hBaseUtils.incDatas(connection, "test", showClickLog.toKeyValue() , familyColumn);
    }
}
