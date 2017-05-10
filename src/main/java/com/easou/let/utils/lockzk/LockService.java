package com.easou.let.utils.lockzk;

import clojure.lang.Obj;
import com.easou.let.common.Constants;
import com.easou.let.utils.PropertiesUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by ES-BF-IT-126 on 2017/5/3.
 */
public class LockService {
    private PropertiesUtils propertiesUtils = PropertiesUtils.getInstance();
    private Logger logger = LoggerFactory.getLogger("operation");
    private static Object lock = new Object();

    /**
     * Do service.
     *
     * @param doTemplate the do template
     */
    public void doService(DoTemplate doTemplate){
        //获得zookeeper地址
        String zkServer = propertiesUtils.getProperties("zookeeper_server");
        String selfLockPath = propertiesUtils.getProperties("selfLockPath");
        AbstractZookeeper abstractZookeeper = new AbstractZookeeper();
        ZooKeeper zooKeeper = abstractZookeeper.getZookeeperCon(zkServer, Constants.SessionTimeOut);
        DistributedLock distributedLock = new DistributedLock(zooKeeper);
        LockWatcher lockWatcher = new LockWatcher(doTemplate, distributedLock);
        distributedLock.setWatcher(lockWatcher);
        try {
            synchronized (lock){
                if(null == zooKeeper.exists(selfLockPath, lockWatcher)){
                    //如果无根目录，则建立永久根目录
                    String path = zooKeeper.create(selfLockPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    logger.info("create lockRootPath success：" + path);
                }
            }
            if(distributedLock.getLock()){
                logger.info(distributedLock.subCurrentPath+"开工，干活！ Service");
                lockWatcher.excute();
                logger.info(distributedLock.subCurrentPath+"活干完了，收起禁止通行标，撤了！Service");
                distributedLock.unLock();
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
