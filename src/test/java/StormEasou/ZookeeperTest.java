
package StormEasou;

import com.easou.let.utils.lockzk.AbstractZookeeper;
import com.easou.let.utils.lockzk.DistributedLock;
import com.easou.let.utils.lockzk.DoTemplate;
import com.easou.let.utils.lockzk.LockService;
import org.slf4j.Logger;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by ES-BF-IT-126 on 2017/5/4.
 */
public class ZookeeperTest {
    private static final String connectionServer = "hadoop1:2181,hadoop2:2181,hadoop3:2181";
    private static final int sessionTimeOut = 10000;

    /**
     * The Logger.
     */
    Logger logger = LoggerFactory.getLogger("operation");

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println("ddd");
    }

    /**
     * Gets distribute lock.
     *
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void getDistributeLock() throws IOException, InterruptedException {

        logger.info("Zookeeper test start");
        for(int i = 0 ; i< 10 ;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new LockService().doService(new DoTemplate() {
                        @Override
                        public void excute() {
                            logger.info(Thread.currentThread().getName()+"执行成功");
                        }
                    });
                }
            }).start();
        }
        Thread.sleep(100000);
    }
}
