package com.easou.let.dao;

import com.easou.let.db.DBConnectUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-3
 * Time: 上午11:59
 * To change this template use File | Settings | File Templates.
 */
public class HBaseDao {
    private static HBaseDao hBaseDao = null;
    Admin admin = null;
    private HBaseDao(){}
    public static HBaseDao getInstance(){
        hBaseDao = new HBaseDao();
        return hBaseDao;
    }

    // Hbase获取所有的表信息
    public List getAllTables() {
        List<String> tables = null;
        if (admin != null) {
            try {
                HTableDescriptor[] allTable = admin.listTables();
                if (allTable.length > 0)
                    tables = new ArrayList<String>();
                for (HTableDescriptor hTableDescriptor : allTable) {
                    tables.add(hTableDescriptor.getNameAsString());
                    System.out.println(hTableDescriptor.getNameAsString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tables;
    }

    /**
     * 添加表
     * @param hBaseAdmin
     * @param table
     * @throws IOException
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
        System.out.println("is existsTable" + isExists);
        return isExists;
    }

    /**
     * 添加数据
     * @param tableName
     * @param rowKey
     * @param value
     * @param family
     * @param qualify
     * @throws IOException
     */
    public void putDatas(String tableName, String rowKey, String value, byte [] family, byte [] qualify) throws IOException{
        Table table = null;
        if ( rowKey != null && !"".equals(rowKey)) {
            Connection connection = DBConnectUtil.open();
            table = connection.getTable(TableName.valueOf(tableName));
            Put put = new Put(rowKey.getBytes());// 一个PUT代表一行数据，再NEW一个PUT表示第二行数据,每行一个唯一的ROWKEY，此处rowkey为put构造方法中传入的值
            put.addColumn(family, qualify, value.toString().getBytes());// 本行数据的第一列
            table.put(put);
        }
    }

    /**
     * 查询数据
     * @param tableName
     * @param rowKey
     * @param family
     * @param qualify
     * @return
     * @throws IOException
     */
    public byte[] selectDatas(String tableName, String rowKey, byte [] family, byte [] qualify) throws IOException{
        if(StringUtils.isNotEmpty(tableName) && StringUtils.isNotEmpty(rowKey) && (family.length > 0) && (qualify.length > 0) ){
            Connection connection = DBConnectUtil.open();
            Table table = connection.getTable(TableName.valueOf(tableName));
            Get get = new Get(rowKey.getBytes());
            Result result = table.get(get);
            byte[] value = result.getValue(family, qualify);
            return value;
        }
        return null;
    }
}
