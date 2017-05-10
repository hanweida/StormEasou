package com.easou.let.db;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-2
 * Time: 下午2:51
 * To change this template use File | Settings | File Templates.
 */
public class Config {
    /**
     * The constant configuration.
     */
    public static Configuration configuration;
    /**
     * The constant con.
     */
    public static Connection con;
    static {
        configuration = HBaseConfiguration.create();
            configuration.set("hbase.zookeeper.property.clientPort", "2181");
            configuration.set("hbase.zookeeper.quorum", "hadoop1");
        try {
            con = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
