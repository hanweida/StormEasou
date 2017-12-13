package com.easou.let.fun;

import backtype.storm.task.IMetricsContext;
import com.easou.let.db.trident.DBFile;
import org.apache.log4j.Logger;
import org.apache.storm.shade.org.eclipse.jetty.io.RuntimeIOException;
import storm.trident.state.State;
import storm.trident.state.StateFactory;
import storm.trident.state.map.IBackingMap;
import storm.trident.state.map.MapState;
import storm.trident.state.map.TransactionalMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

    public class CusMapState implements MapState {
    private static final Logger logger = Logger.getLogger("operation");
    private MapState mapState;
    public CusMapState(){
        mapState = (TransactionalMap) TransactionalMap.build(new MemoryMapStateBacking());
    }

    @Override
    public List multiUpdate(List keys, List list) {
        logger.info("invoke: [CusMapState] 【multiUpdate】keys" + keys.toString());
        logger.info("invoke: [CusMapState] 【multiUpdate】list" + list.toString());
        return mapState.multiUpdate(keys, list);
    }

    @Override
    public void multiPut(List keys, List vals) {
        logger.info("invoke: [CusMapState] 【multiPut】keys" + keys.toString());
        logger.info("invoke: [CusMapState] 【multiPut】vals" + vals.toString());
        mapState.multiPut(keys, vals);
    }

    @Override
    public List multiGet(List keys) {
        logger.info("invoke: [CusMapState] 【multiGet】" + keys.toString());
        return mapState.multiGet(keys);
    }

    @Override
    public void beginCommit(Long txid) {
        logger.info("invoke: [CusMapState] 【beginCommit】"+txid);
        mapState.beginCommit(txid);
    }

    @Override
    public void commit(Long txid) {
        logger.info("invoke: [CusMapState] 【commit】" + txid);
        mapState.commit(txid);
    }

    static class MemoryMapStateBacking<T> implements IBackingMap<T>{

        Map<List<Object>, T> db;
        DBFile dbFile = new DBFile();

        public MemoryMapStateBacking(){
            logger.info("invoke [MemoryMapStateBacking]");
            this.db = new HashMap<List<Object>, T>();
        }

        @Override
        public List<T> multiGet(List<List<Object>> keys) {
            logger.info(("invoke [MemoryMapStateBacking] 【multiGet】"));
            List<T> ret = new ArrayList<T>();
            for(List<Object> key : keys){
                ret.add(null);
            }
            return ret;
        }

        @Override
        public void multiPut(List<List<Object>> keys, List<T> vals) {
            logger.info(("invoke [MemoryMapStateBacking] 【multiPut】"));
            logger.info(("invoke [MemoryMapStateBacking] 【multiPut】keys" + keys.toString()));
            for(int i = 0; i < keys.size(); i++){
                logger.info(("KeysSize:" + keys.size()));
                List<Object> key = keys.get(i);
                T val = (T)vals.get(i);
                logger.info(("invoke [MemoryMapStateBacking] 【multiPut】vals" + vals.toString()));
                try {
                    dbFile.writer(key, val);
                } catch (IOException e) {
                    e.printStackTrace();
                    //  throw new FailedException();
                } catch (RuntimeException exception){
                    System.out.println("RUNTIME EXCEPTION");
                    exception.printStackTrace();
                }
            }
        }
    }

    public static class Factory implements StateFactory {
        String _id;

        public Factory() {
            _id = UUID.randomUUID().toString();
        }

        @Override
        public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
            return new CusMapState();
        }
    }
}
