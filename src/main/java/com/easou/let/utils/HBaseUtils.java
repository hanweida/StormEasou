package com.easou.let.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ES-BF-IT-126 on 2017/5/10.
 */
public class HBaseUtils {
    private HBaseUtils(){}
    private static HBaseUtils hBaseUtils = new HBaseUtils();
    public static HBaseUtils getInstance(){
        return hBaseUtils;
    }


    /**
     * @des:
     * @author:ES-BF-IT-126
     * @method:getHBaseConn
     * @date:Date 2017/5/10
     * @params:[]
     * @returns:org.apache.hadoop.hbase.client.Connection
     */
    public Connection getHBaseConn() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "hadoop1,hadoop2,hadoop3");
        Connection connection = ConnectionFactory.createConnection(configuration);
        return connection;
    }

    /**
     * Hbase获取所有的表信息
     * @author:ES-BF-IT-126
     * @method:getAllTables
     * @date:Date 2017/5/10
     * @params:[admin]
     * @returns:java.util.List
     */
    public List<String> getAllTables(Admin admin) {
        List<String> tables = null;
        if (admin != null) {
            try {
                HTableDescriptor[] allTable = admin.listTables();
                if (allTable.length > 0)
                    tables = new ArrayList<String>();
                for (HTableDescriptor hTableDescriptor : allTable) {
                    tables.add(hTableDescriptor.getNameAsString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tables;
    }

    /**
     * 添加表
     * @author:ES-BF-IT-126
     * @method:addTable
     * @date:Date 2017/5/10
     * @params:[hBaseAdmin, table]
     * @returns:void
     */
    public void addTable(Admin hBaseAdmin, String table) throws IOException{
        TableName tableName = TableName.valueOf(table);
        HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
        HColumnDescriptor hColumnDescriptor = new HColumnDescriptor("info");
        hTableDescriptor.addFamily(hColumnDescriptor.setCompressionType(Compression.Algorithm.NONE));
        if(!existsTable(hBaseAdmin, table)){
            hBaseAdmin.createTable(hTableDescriptor);
        }
    }

    /**
     * 删除表
     * @param hBaseAdmin
     * @param tableName
     * @throws IOException
     */
    public void deleteTables(HBaseAdmin hBaseAdmin, String tableName) throws IOException{
        hBaseAdmin.disableTable(tableName);
        hBaseAdmin.deleteTable(tableName);
    }

    /**
     * 删除表通过 TableName 类
     * @param hBaseAdmin
     * @param tableName
     * @throws IOException
     */
    public void deleteTableByTableName(HBaseAdmin hBaseAdmin, String tableName) throws IOException{
        TableName tableName1 = TableName.valueOf(tableName);
        hBaseAdmin.disableTable(tableName1);
        hBaseAdmin.deleteTable(tableName1);
    }

    /**
     * 是否存在表
     * @param hBaseAdmin
     * @param tableName
     * @throws IOException
     */
    public boolean existsTable(Admin hBaseAdmin, String tableName) throws IOException{
        boolean isExists = hBaseAdmin.tableExists(TableName.valueOf(tableName));
        return isExists;
    }

    /**
     * 添加数据
     * @param hConnection
     * @param tableName
     */
    public void putDatas(Connection hConnection, String tableName) throws IOException{

        String [] rows = {"0000_000_00000","1111_111_11111"};
        String [] column = {"userid", "mmid", "date"};
        String [][] values = {{"0000", "0000", "00000000"}, {"1111", "111", "11111111"}};
        TableName tableName1 = TableName.valueOf(tableName);
        byte [] family = Bytes.toBytes("date");
        Table Table = hConnection.getTable(tableName1);
        for(int i = 0 ;i < rows.length ; i++){
            System.out.println("==============" + rows[i]);
            byte [] rowkey = rows[i].getBytes();
            Put put = new Put(rowkey);
            for(int j = 0 ; j < column.length; j++){
                byte [] qualifier = Bytes.toBytes(column[j]);
                byte [] value = Bytes.toBytes(values[i][j]);
                put.add(family, qualifier, value);
            }
            Table.put(put);
        }
        Table.close();
    }

    /**
     * 查询数据
     * @param hConnection
     * @param tableName
     * @throws IOException
     */
    public void selectDatas(Connection hConnection, String tableName) throws IOException{
        TableName tableName1 = TableName.valueOf(tableName);
        Table table = hConnection.getTable(tableName1);
        Get get = new Get("20161031_2_1_1_4560_1299".getBytes());
        Result result = table.get(get);
        List list = result.listCells();
        List<Cell> listCell =  result.getColumnCells("info".getBytes(),"shownum".getBytes());
        System.out.println(Bytes.toString(result.getValue("info".getBytes(),"shownum".getBytes())));
        //System.out.println(listCell.get(0));
    }
}
