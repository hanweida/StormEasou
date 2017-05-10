package com.easou.let.utils.lockzk;

import com.easou.let.utils.PropertiesUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * 分布式锁，通过zookeeper对象，对zookeeper加锁，解锁等操作
 * Created by ES-BF-IT-126 on 2017/5/3.
 */
public class DistributedLock{
    private ZooKeeper zooKeeper = null;
    private Watcher watcher;
    private PropertiesUtils propertiesUtils = PropertiesUtils.getInstance();
    private String lockPath;
    private String subLockPath;
    /**
     * The Sub current path.
     */
    public String subCurrentPath;
    private String waitePath;
    /**
     * The Logger.
     */
    Logger logger = LoggerFactory.getLogger("operation");

    /**
     * Instantiates a new Distributed lock.
     */
    public DistributedLock(){
    }

    /**
     * 设置zookeeper
     *
     * @param zooKeeper the zoo keeper
     */
    public DistributedLock(ZooKeeper zooKeeper){
        this.zooKeeper = zooKeeper;
    }

    /**
     * 设置观察者
     *
     * @param watcher the watcher
     */
    public void setWatcher(Watcher watcher){
        this.watcher = watcher;
    }

    /**
     * Get waite path string.
     *
     * @return the string
     */
    public String getWaitePath(){return this.waitePath;}

    /**
     * 获取锁
     *
     * @return boolean
     */
    public boolean getLock(){
        lockPath = propertiesUtils.getProperties("selfLockPath");
        subLockPath = propertiesUtils.getProperties("selfLockSubPath");
        //建立路径
        if(StringUtils.isNotEmpty(subLockPath)){
            try {
                subCurrentPath = zooKeeper.create(subLockPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL  );
                logger.info("getLock subCurrentPath" + subCurrentPath);
                //如果是最小节点，则返回true，否则返回false
                if(checkMinPath()){
                    return true;
                }
                return false;
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 释放锁
     */
    public void unLock(){
        try {
            //节点不存在
            if(null == zooKeeper.exists(subCurrentPath, false)){
                return;
            }
            //如果存在节点，则删除节点
            zooKeeper.delete(this.subCurrentPath, -1);
            //zookeeper 关闭连接
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查自己是不是最小的节点
     *
     * @return boolean
     * @throws KeeperException      the keeper exception
     * @throws InterruptedException the interrupted exception
     */
    public boolean checkMinPath() throws KeeperException, InterruptedException {
        List<String> childList = zooKeeper.getChildren(lockPath, false);
        if(null != childList){
            Collections.sort(childList);
            int index = childList.indexOf(subCurrentPath.substring(lockPath.length() + 1));
            switch (index){
                case -1 : logger.info("该节点消失");return false;
                case 0 : return true;
                default:
                    this.waitePath = lockPath+"/"+childList.get(index-1);
                    logger.info("获取排在我前面的节点"+waitePath);
                    try{
                        zooKeeper.getData(waitePath, watcher, null);
                        return false;
                    } catch (KeeperException e){
                        if(null == zooKeeper.exists(waitePath, false)){
                            return checkMinPath();
                        } else {
                            throw e;
                        }
                    }
            }
        }
        return false;
    }


}
