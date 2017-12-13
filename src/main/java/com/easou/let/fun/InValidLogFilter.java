package com.easou.let.fun;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

/**
 * 对日志过滤，如果为空或者 行为@EE0@，则过滤
 * Created by ES-BF-IT-126 on 2017/7/27.
 */
public class InValidLogFilter extends BaseFilter {
    private static final Logger logger = Logger.getLogger("operation");
    private static int count = 0;

    @Override
    public boolean isKeep(TridentTuple tuple) {
        //返回true，则通过，返回false 则过滤掉
        String line = tuple.getString(0);
        //logger.info("InValidLogFilter Count:" + count++);
        if(StringUtils.isEmpty(line) || "@EE@".equals(line)){
            return false;
        } else {
            return true;
        }
    }
}
