package com.easou.let.fun;

import backtype.storm.task.IMetricsContext;
import storm.trident.state.State;
import storm.trident.state.StateFactory;

import java.util.Map;

/**
 * Created by ES-BF-IT-126 on 2017/7/27.
 */
public class CdpStateFactory implements StateFactory {
    @Override
    public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
        return new CdpStateQuery();
    }
}
