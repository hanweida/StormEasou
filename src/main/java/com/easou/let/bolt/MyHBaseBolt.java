package com.easou.let.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import com.easou.let.db.Config;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-2
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
public class MyHBaseBolt extends BaseRichBolt {
    private OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void execute(Tuple input) {
        try {
            ////String id = input.getStringByField("word");
            //Integer mesg = input.getIntegerByField("count");
            //System.out.println("Count："+mesg);
            Table table = Config.con.getTable(TableName.valueOf("t_showclicklog"));
            Put put = new Put("10".getBytes());// 一个PUT代表一行数据，再NEW一个PUT表示第二行数据,每行一个唯一的ROWKEY，此处rowkey为put构造方法中传入的值
            put.addColumn("info".getBytes(), "a".getBytes(),
                    "123".getBytes());// 本行数据的第一列
            table.put(put);

        } catch (Exception e) {
            e.printStackTrace(); // To change body of catch statement use File |
            collector.fail(input);                  // Settings | File Templates.
        }
        //collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
