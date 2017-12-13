package hbase;

import com.easou.let.utils.HBaseUtils;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-4
 * Time: 下午2:59
 * To change this template use File | Settings | File Templates.
 */
public class HbaseUtilTest {
    /**
     * Test.
     *
     * @throws IOException the io exception
     */
    @Test
    public void test() throws IOException {
        //MediaIncomeDao hBaseDao = MediaIncomeDao.getInstance();
        //hBaseDao.getAllTables();
        //System.out.println(StormLetConfiguration.getProperties("wordsFile"));

       // HBaseDao hBaseDao = HBaseDao.getInstance();
        //hBaseDao.putDatas("t_showclicklog", "10", "1234", "info".getBytes(), "a".getBytes());
    }

    /**
     * Test get.
     *
     * @throws IOException the io exception
     */
    @Test
    public void testGet() throws IOException {
        /*HBaseDao hBaseDao = HBaseDao.getInstance();
        byte[] showNumValue = hBaseDao.selectDatas("t_showclicklog", "20161109005015001633180036491000024039325009232361000962086000000000962086001", "info".getBytes(), EasouConstants.HBASE_SHOWNUM.getBytes());
        System.out.println(Bytes.toInt(showNumValue));*/
        HBaseUtils hBaseUtils = HBaseUtils.getInstance();
        Connection connection = hBaseUtils.getHBaseConn();
    }

    @Test
    public void testIncMulti() throws IOException, InterruptedException {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HBaseUtils hBaseUtils = HBaseUtils.getInstance();
                try {
                    Connection connection = hBaseUtils.getHBaseConn();
                    Map<String, Map<String, Long>> familyColumn = new HashMap<String, Map<String, Long>>();
                    Map<String, Long> qualifier = new HashMap<String, Long>();
                    qualifier.put("show", 1l);
                    familyColumn.put("info", qualifier);
                    hBaseUtils.incDatas(connection, "test", "counts" , familyColumn);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } ;


        for(int i = 0 ; i < 1000; i ++){
            Thread thread = new Thread(runnable);
            thread.start();
        }
        Thread.sleep(10000000);

    }

    @Test
    public void getDatas() throws IOException {
        HBaseUtils hBaseUtils = HBaseUtils.getInstance();
        Connection connection = hBaseUtils.getHBaseConn();
        hBaseUtils.getIncDatas(connection, "test", "counts");
    }

    @Test
    public void getDates(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        date.setTime(Long.valueOf("1495641208032"));
        System.out.println(sdf.format(date));
    }

    @Test
    public void queryAllDataByTable() throws IOException {
        HBaseUtils hBaseUtils = HBaseUtils.getInstance();
        Connection connection = hBaseUtils.getHBaseConn();
        hBaseUtils.queryAllDatas(connection, "test");
    }

    @Test
    public void queryAllDataByRow() throws IOException {
        HBaseUtils hBaseUtils = HBaseUtils.getInstance();
        Connection connection = hBaseUtils.getHBaseConn();
        hBaseUtils.queryTableByRowKey(connection, "test", "2017-05-25_10545_164700_3687089_24894761_9292955_5899797_1_0_0");
    }
}
