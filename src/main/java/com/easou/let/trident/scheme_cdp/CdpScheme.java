package com.easou.let.trident.scheme_cdp;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by ES-BF-IT-126 on 2017/8/9.
 */
public class CdpScheme implements Scheme {
    private static final Logger logger = Logger.getLogger("operation");

    @Override
    public List<Object> deserialize(byte[] bytes) {
        String msg = null;
        try {
            msg = new String(bytes, "UTF-8");
            //logger.info("CdpScheme : "+msg);
            return new Values(msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("cdpTridentFields");
    }
}
