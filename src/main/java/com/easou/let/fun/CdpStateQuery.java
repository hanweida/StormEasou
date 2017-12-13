package com.easou.let.fun;


import org.apache.log4j.Logger;
import storm.trident.state.State;

/**
 * Created by ES-BF-IT-126 on 2017/7/27.
 */
public class CdpStateQuery implements State {
    Logger logger = Logger.getLogger("operation");

    @Override
    public void beginCommit(Long txid) {
        logger.info("beginCommit："+txid);
    }

    @Override
    public void commit(Long txid) {
        logger.info("commit："+txid);
    }

    public String getCdp(String a){
        return a;
    }
}
