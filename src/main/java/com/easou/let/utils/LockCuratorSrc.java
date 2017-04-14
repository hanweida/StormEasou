package com.easou.let.utils;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-23
 * Time: 下午5:31
 * To change this template use File | Settings | File Templates.
 */
public class LockCuratorSrc {
    private static CuratorFramework client = null;

    public synchronized  static CuratorFramework getCF(){
        if(client==null){
            try {
                RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
                System.out.println("create client--------");
                client = CuratorFrameworkFactory.newClient("192.168.100.42:2181", retryPolicy);
                client.start();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return client;
    }
}
