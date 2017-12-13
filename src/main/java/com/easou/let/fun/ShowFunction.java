package com.easou.let.fun;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 * Created by ES-BF-IT-126 on 2017/7/26.
 */
public class ShowFunction extends BaseFunction {
    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        String str = tuple.getString(0);
        System.out.println(str);

        collector.emit(new Values(str));
    }
}
