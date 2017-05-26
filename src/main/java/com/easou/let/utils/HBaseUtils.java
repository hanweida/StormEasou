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
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public void createTable(Admin hBaseAdmin, String table, List<String> familyList) throws IOException{
        TableName tableName = TableName.valueOf(table);
        HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
        for(String family : familyList){

        }
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
     * 添加数据
     * @param hConnection
     * @param tableName
     */
    public void putDatas(Connection hConnection, String tableName, String str) throws IOException{

        String [] rows = {"0000_000_00000","1111_111_11111"};
        String [] column = {"userid", "mmid", "date"};
        String [][] values = {{str, str, str}, {str, str, str}};
        TableName tableName1 = TableName.valueOf(tableName);
        Table Table = hConnection.getTable(tableName1);

        byte [] family = Bytes.toBytes("info");
        for(int i = 0 ;i < rows.length ; i++){
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
        Get get = new Get("counts".getBytes());
        Result result = table.get(get);
        List list = result.listCells();
        List<Cell> listCell =  result.getColumnCells("info".getBytes(),"shownum".getBytes());
        System.out.println(Bytes.toString(result.getValue("info".getBytes(),"shownum".getBytes())));
        //System.out.println(listCell.get(0));
    }


    /**
     * 多计数器，原子性添加数据
     * @author:ES-BF-IT-126
     * @method:incDatas
     * @date:Date 2017/5/17
     * @params:[hConnection, tableName, row, faimlycolum]
     * @returns:void
     */
    public void incDatas(Connection hConnection , String tableName, String row, Map<String, Map<String, Long>> faimlycolum) throws IOException {
        Table table = hConnection.getTable(TableName.valueOf(tableName));
        // IncrementMultipleExample
        Increment increment = new Increment(Bytes.toBytes(row));
        Set<String> set = faimlycolum.keySet();
        Iterator<String> iterator = set.iterator();
        String columnFamily = "";
        while (iterator.hasNext()){
            columnFamily = iterator.next();
            Map<String, Long> qualifierMap = faimlycolum.get(columnFamily);
            Set<String> qualifierKeySet = qualifierMap.keySet();
            for(String qualifier : qualifierKeySet){
                increment.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier), qualifierMap.get(qualifier));
                //System.out.println(""+qualifierMap.get(qualifier).byteValue());
                //qualifierMap.get(qualifier).byteValue()
            }
        }

        Result result1 = table.increment(increment);
        for (Cell cell : result1.rawCells()) {
            System.out.println(Thread.currentThread().getName()+"Cell: " + cell +
                    " Value: " + Bytes.toLong(cell.getValueArray(), cell.getValueOffset(),
                    cell.getValueLength())); // co IncrementMultipleExample-3-Dump1 Print the cell and returned counter value.
        }
        table.close();
        hConnection.close();
    }

    public void getIncDatas(Connection hConnection, String tableName, String row) throws IOException{
        TableName tableName1 = TableName.valueOf(tableName);
        Table table = hConnection.getTable(tableName1);
        Get get = new Get("counts".getBytes());
        Result result = table.get(get);
        for (Cell cell : result.rawCells()) {
            System.out.println("Cell: " + cell +
                    " Value: " + Bytes.toLong(cell.getValueArray(), cell.getValueOffset(),
                    cell.getValueLength())); // co IncrementMultipleExample-3-Dump1 Print the cell and returned counter value.
        }
    }
}
