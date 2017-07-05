package com.easou.let.utils.lockzk;


import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ES-BF-IT-126 on 2017/5/3.
 */
public class LockWatcher implements Watcher {
    /**
     * The Logger.
     */
    Logger logger = LoggerFactory.getLogger("operation");
    /**
     * The Distributed lock.
     */
    public DistributedLock distributedLock;
    private DoTemplate doTemplate;

    /**
     * Instantiates a new Lock watcher.
     *
     * @param doTemplate      the do template
     * @param distributedLock the distributed lock
     */
    public LockWatcher(DoTemplate doTemplate, DistributedLock distributedLock){
        this.distributedLock = distributedLock;
        this.doTemplate = doTemplate;
    }

    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getType() == Event.EventType.NodeDeleted  && distributedLock.getWaitePath().equals(watchedEvent.getPath())){
            //监控执行线程，如果上一个节点没了，则判断是否是最小节点，如果是的话，则执行操作
            //logger.info("前面的弟兄不见了，咱可以看看去");
            try {
                if(distributedLock.checkMinPath()){
                    //logger.info(distributedLock.subCurrentPath+"开工，干活！Wathcer");
                    //最小节点可以执行，
                    excute();
                    //logger.info(distributedLock.subCurrentPath+"活干完了，收起禁止通行标，撤了！Wathcer");
                    distributedLock.unLock();
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 监测最小节点，执行
     */
    public void excute(){

        doTemplate.excute();
    }
}
