package com.easou.let.dao;

import com.easou.let.db.DBConnectUtil;
import com.easou.let.pojo.MediaIncomeAdmin;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-4
 * Time: 下午2:36
 * To change this template use File | Settings | File Templates.
 */
public class MediaIncomeDao {
    private static MediaIncomeDao hBaseDao = null;
    private MediaIncomeDao(){}
    public static MediaIncomeDao getInstance(){
        hBaseDao = new MediaIncomeDao();
        return hBaseDao;
    }

    // Hbase获取所有的表信息
    public List getAllTables() throws IOException {
        Connection connection = DBConnectUtil.open();
        Admin admin = connection.getAdmin();
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
     * 添加数据
     * @param hConnection
     * @param mediaIncomeAdmin
     */
    public void putDatas(Connection hConnection, MediaIncomeAdmin mediaIncomeAdmin, String[] column, String[] values) throws IOException{
        //date_userid_mediaid _source_adtype_promotiontype
        //20161103_14560_1234_01_02_3
        String rows = mediaIncomeAdmin.getDate() + mediaIncomeAdmin.getUserid() +
                mediaIncomeAdmin.getMediaid() + mediaIncomeAdmin.getSource() + mediaIncomeAdmin.getAdtype() + mediaIncomeAdmin.getPromotionType();
        TableName tableName1 = TableName.valueOf("t_media_income_admin");
        byte [] family = Bytes.toBytes("info");
        Table table = hConnection.getTable(tableName1);
            byte [] rowkey = rows.getBytes();
            Put put = new Put(rowkey);
            for(int j = 0 ; j < column.length; j++){
                byte [] qualifier = Bytes.toBytes(column[j]);
                byte [] value = Bytes.toBytes(values[j]);
                put.add(family, qualifier, value);
                table.put(put);
            }
        table.close();
    }

    /**
     * 查询数据
     * @param hConnection
     * @param hConnection
     * @param mediaIncomeAdmin
     * @throws IOException
     */
    public void selectDatas(Connection hConnection, MediaIncomeAdmin mediaIncomeAdmin) throws IOException{
        TableName tableName1 = TableName.valueOf("t_media_income_admin");
        String rows = mediaIncomeAdmin.getDate() + mediaIncomeAdmin.getUserid() +
                mediaIncomeAdmin.getMediaid() + mediaIncomeAdmin.getSource() + mediaIncomeAdmin.getAdtype() + mediaIncomeAdmin.getPromotionType();

        Table table = hConnection.getTable(tableName1);
        Get get = new Get(rows.getBytes());
        Result result = table.get(get);
        List list = result.listCells();
        List<Cell> listCell =  result.getColumnCells("info".getBytes(),"shownum".getBytes());
        System.out.println(Bytes.toString(result.getValue("info".getBytes(),"shownum".getBytes())));
        //System.out.println(listCell.get(0));
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

}
