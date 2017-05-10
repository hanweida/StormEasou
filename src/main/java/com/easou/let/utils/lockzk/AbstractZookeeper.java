package com.easou.let.utils.lockzk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper生成类
 * Created by Jerry on 2017/5/3.
 */
public class AbstractZookeeper implements Watcher {
    private CountDownLatch zkWatchLatch = new CountDownLatch(1);
    /**
     * The Logger.
     */
    Logger logger = LoggerFactory.getLogger("operation");

    /**
     * Get zookeeper con zoo keeper.
     *
     * @param zkServers      the zk servers
     * @param sessionTimeOut the session time out
     * @return the zoo keeper
     */
    public ZooKeeper getZookeeperCon(String zkServers, int sessionTimeOut){
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper(zkServers, sessionTimeOut,this);
            try {
                zkWatchLatch.await();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("Zookeeper connected success"+Thread.currentThread().getName());
        return zooKeeper;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
            zkWatchLatch.countDown();
        }
    }
}
