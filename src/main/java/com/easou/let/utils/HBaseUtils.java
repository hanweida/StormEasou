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
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
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
     * @author: ES-BF-IT-126
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

    /**
     * 查询全部数据，根据表名，查询整表数据
     * @author:ES-BF-IT-126
     * @method:queryAllDatas
     * @date:Date 2017/8/30
     * @params:[hConnection, tableName]
     * @returns:void
     */
    public void queryAllDatas(Connection hConnection, String tableName) throws IOException {
        TableName tableName1 = TableName.valueOf(tableName);
        Table table = hConnection.getTable(tableName1);
        //取得表中所有数据
        ResultScanner resultScanner = table.getScanner(new Scan());
        //循环输出表中的数据
        long total = 0l;
        for(Result result : resultScanner){
            byte[] row = result.getRow();
            System.out.println("row value is : " + new String(row));

            for(Cell cell :result.rawCells()){
                System.out.println("getFamilyArray:" + Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(),
                        cell.getFamilyLength()));
                System.out.println("getQualifierArray:" + Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
                        cell.getQualifierLength()));
                System.out.println("getValueArray:" + Bytes.toLong(cell.getValueArray(), cell.getValueOffset(),
                        cell.getValueLength()));
                total+=                    Bytes.toLong(cell.getValueArray(), cell.getValueOffset(),
                        cell.getValueLength());
            }

            List<Cell> cellList = result.listCells();
            for(Cell cell : cellList){
                byte[] familyArray = cell.getFamilyArray();
                //System.out.println(new String(familyArray));
                byte[] qualifierArryay = cell.getQualifierArray();
                byte[] valueArray = cell.getValueArray();
                //System.out.println("row value is : " + new String(valueArray));
            }
        }
        System.out.println("total："+total);

    }

    /**
     * 按行键查询表数据
     */
    public void queryTableByRowKey(Connection hConnection, String tableName, String row) throws IOException{

        System.out.println("---------------按行键查询表数据 START-----------------");

        // 取得数据表对象
        Table table = hConnection.getTable(TableName.valueOf(tableName));

        // 新建一个查询对象作为查询条件
        Get get = new Get(row.getBytes());

        // 按行键查询数据
        Result result = table.get(get);

        byte[] rows = result.getRow();
        System.out.println("row key is:" + new String(rows));

        for(Cell cell : result.rawCells()){
            System.out.println("getFamilyArray:" + Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(),
                    cell.getFamilyLength()));
            System.out.println("getQualifierArray:" + Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
                    cell.getQualifierLength()));
            System.out.println("getValueArray:" + Bytes.toLong(cell.getValueArray(), cell.getValueOffset(),
                    cell.getValueLength()));
        }
        System.out.println("---------------按行键查询表数据 END-----------------");
    }

    /**
     * 按条件查询表数据
     */
    public void queryTableByCondition(Connection hConnection, String tableName, String family, String qualifier, String value) throws IOException{

        System.out.println("---------------按条件查询表数据 START-----------------");

        // 取得数据表对象
        Table table = hConnection.getTable(TableName.valueOf(tableName));

        // 创建一个查询过滤器
        Filter filter = new SingleColumnValueFilter(Bytes.toBytes(family), Bytes.toBytes(qualifier),
                CompareFilter.CompareOp.EQUAL, Bytes.toBytes(value));

        // 创建一个数据表扫描器
        Scan scan = new Scan();

        // 将查询过滤器加入到数据表扫描器对象
        scan.setFilter(filter);

        // 执行查询操作，并取得查询结果
        ResultScanner scanner = table.getScanner(scan);

        // 循环输出查询结果
        for (Result result : scanner) {
            byte[] row = result.getRow();
            System.out.println("row key is:" + new String(row));

            List<Cell> listCells = result.listCells();
            for (Cell cell : listCells) {

                byte[] familyArray = cell.getFamilyArray();
                byte[] qualifierArray = cell.getQualifierArray();
                byte[] valueArray = cell.getValueArray();

                System.out.println("row value is:" + new String(familyArray) + new String(qualifierArray)
                        + new String(valueArray));
            }
        }

        System.out.println("---------------按条件查询表数据 END-----------------");

    }

    /**
     * 清空表
     */
    public void truncateTable(HBaseAdmin admin, String tableNameStr) throws IOException{

        System.out.println("---------------清空表 START-----------------");

        // 取得目标数据表的表名对象
        TableName tableName = TableName.valueOf(tableNameStr);

        // 设置表状态为无效
        admin.disableTable(tableName);
        // 清空指定表的数据
        admin.truncateTable(tableName, true);

        System.out.println("---------------清空表 End-----------------");
    }

    /**
     * 删除行
     */
    public void deleteByRowKey(Connection connection, String tableName, String row) throws IOException{

        System.out.println("---------------删除行 START-----------------");

        // 取得待操作的数据表对象
        Table table = connection.getTable(TableName.valueOf(tableName));

        // 创建删除条件对象
        Delete delete = new Delete(Bytes.toBytes(row));

        // 执行删除操作
        table.delete(delete);

        System.out.println("---------------删除行 End-----------------");

    }

    /**
     * 删除行（按条件）
     */
    public void deleteByCondition() throws IOException{

        System.out.println("---------------删除行（按条件） START-----------------");

        // 步骤1：调用queryTableByCondition()方法取得需要删除的数据列表

        // 步骤2：循环步骤1的查询结果，对每个结果调用deleteByRowKey()方法

        System.out.println("---------------删除行（按条件） End-----------------");

    }

    /**
     * 新建列族
     */
    public void addColumnFamily(HBaseAdmin admin, String tableNameStr, String family) throws IOException{

        System.out.println("---------------新建列族 START-----------------");

        // 取得目标数据表的表名对象
        TableName tableName = TableName.valueOf(tableNameStr);

        // 创建列族对象
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(family);

        // 将新创建的列族添加到指定的数据表
        admin.addColumn(tableName, columnDescriptor);

        System.out.println("---------------新建列族 END-----------------");
    }

    /**
     * 删除列族
     */
    public void deleteColumnFamily(HBaseAdmin admin, String tableNameStr, String family) throws IOException{
        System.out.println("---------------删除列族 START-----------------");

        // 取得目标数据表的表名对象
        TableName tableName = TableName.valueOf(tableNameStr);

        // 删除指定数据表中的指定列族
        admin.deleteColumn(tableName, family.getBytes());

        System.out.println("---------------删除列族 END-----------------");
    }

    /**
     * 插入数据,没设好
     */
    public void insert(Connection connection, String tableName, String row) throws IOException{

        System.out.println("---------------插入数据 START-----------------");

        // 取得一个数据表对象
        Table table = connection.getTable(TableName.valueOf(tableName));

        // 需要插入数据库的数据集合
        List<Put> putList = new ArrayList<Put>();

        Put put;

        // 生成数据集合
        for(int i = 0; i < 10; i++){
            put = new Put(Bytes.toBytes(row + i));
            put.addColumn(Bytes.toBytes("base"), Bytes.toBytes("name"), Bytes.toBytes("bookName" + i));
            putList.add(put);
        }

        // 将数据集合插入到数据库
        table.put(putList);
        System.out.println("---------------插入数据 END-----------------");
    }
}
