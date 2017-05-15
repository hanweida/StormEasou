package hbase;

import com.easou.let.utils.HBaseUtils;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;

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
        hBaseUtils.putDatas(connection, "test", "1");
    }
}
