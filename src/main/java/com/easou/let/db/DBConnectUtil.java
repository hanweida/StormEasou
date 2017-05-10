package com.easou.let.db;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-4
 * Time: 下午2:35
 * To change this template use File | Settings | File Templates.
 */
public class DBConnectUtil {
    private static final Configuration conf = HBaseConfiguration.create();

    private static Connection connection = null;

    /**
     * Open connection.
     *
     * @return the connection
     * @throws IOException the io exception
     */
    public static Connection open() throws IOException {
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "hadoop1");
        if(null == connection){
            connection = ConnectionFactory.createConnection(conf);
        }
        return connection;
    }
}
