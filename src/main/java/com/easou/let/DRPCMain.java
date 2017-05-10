package com.easou.let;

import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;

/**
 * Created by ES-BF-IT-126 on 2017/4/18.
 */
public class DRPCMain {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        LocalDRPC drpc = new LocalDRPC();
        LocalCluster cluster = new LocalCluster();

        //cluster.submitTopology("drpc-demo", conf, builder.createLocalTopology(drpc));

        //System.out.println("Results for 'hello':" + drpc.execute("exclamation", "hello"));

        cluster.shutdown();
        drpc.shutdown();
    }
}
