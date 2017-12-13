package com.easou.let.fun;

import backtype.storm.task.IMetricsContext;
import backtype.storm.tuple.Values;
import com.easou.let.db.trident.DBFile;
import com.easou.let.pojo.ShowClickLog;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import storm.trident.state.ITupleCollection;
import storm.trident.state.OpaqueValue;
import storm.trident.state.State;
import storm.trident.state.StateFactory;
import storm.trident.state.TransactionalValue;
import storm.trident.state.ValueUpdater;
import storm.trident.state.map.IBackingMap;
import storm.trident.state.map.MapState;
import storm.trident.state.map.RemovableMapState;
import storm.trident.state.map.SnapshottableMap;
import storm.trident.state.map.TransactionalMap;
import storm.trident.state.snapshot.Snapshottable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ES-BF-IT-126 on 2017/8/1.
 */
public class CusMemoryMapState<T> implements Snapshottable<T>, MapState<T> {
    private static final Logger logger = Logger.getLogger("operation");

    MemoryMapStateBacking<TransactionalValue> _backing; //支持
    MapState<T> tTransactionalMap ;

    SnapshottableMap<T> _delegate;
    Long _currTx = null;
    List<List<Object>> _removed = new ArrayList();

    public CusMemoryMapState(String id){
        _backing = new MemoryMapStateBacking(id);
        _delegate = new SnapshottableMap(TransactionalMap.build(_backing),  new Values("$MEMORY-MAP-STATE-GLOBAL$"));
        tTransactionalMap = TransactionalMap.build(_backing);
    }

    @Override
    public List<T> multiUpdate(List<List<Object>> keys, List<ValueUpdater> updaters) {
        logger.info("invoke: [CusMemoryMapState] 【multiUpdate】");
        return _delegate.multiUpdate(keys, updaters);
    }

    @Override
    public void multiPut(List<List<Object>> keys, List<T> vals) {
        logger.info("invoke: [CusMemoryMapState] 【multiPut】");
        _delegate.multiPut(keys, vals);
    }

    @Override
    public List<T> multiGet(List<List<Object>> keys) {
        logger.info("invoke: [CusMemoryMapState] 【multiGet】");
        return _delegate.multiGet(keys);
    }

    @Override
    public void beginCommit(Long txid) {
        logger.info("invoke: [CusMemoryMapState] 【beginCommit】" +txid);
        _delegate.beginCommit(txid);
        if(txid == null || !txid.equals(_currTx)){
            _backing.multiRemove(_removed);
        }
        _removed = new ArrayList<List<Object>>();
        _currTx = txid;
    }

    @Override
    public void commit(Long txid) {
        logger.info("invoke: [CusMemoryMapState] 【commit】" +txid);
        _delegate.commit(txid);
    }


    @Override
    public T update(ValueUpdater updater) {
        return _delegate.update(updater);
    }

    @Override
    public void set(T o) {

    }

    @Override
    public T get() {
        return null;
    }

        static ConcurrentHashMap<String, Map<List<Object>, Object>> _dbs = new ConcurrentHashMap<String, Map<List<Object>, Object>>();

    static class MemoryMapStateBacking<T> implements IBackingMap<T>, ITupleCollection{

        Map<List<Object>, T> db;
        Long currTx;
        DBFile dbFile = new DBFile();

        public MemoryMapStateBacking(String id){
            logger.info("invoke [MemoryMapStateBacking]");
            if(!_dbs.contains(id)){
                _dbs.put(id, new HashedMap());
            }
            this.db = (Map<List<Object>, T>) _dbs.get(id);
        }

        public static void clearAll() {
            _dbs.clear();
        }

        //被调用
        public void multiRemove(List<List<Object>> keys) {
            logger.info("invoke [MemoryMapStateBacking]【multiRemove】");
            for(List<Object> key: keys) {
                db.remove(key);
            }
        }

        @Override
        public Iterator<List<Object>> getTuples() {
            return new Iterator<List<Object>>() {

                private Iterator<Map.Entry<List<Object>, T>> it = db.entrySet().iterator();

                public boolean hasNext() {
                    return it.hasNext();
                }

                public List<Object> next() {
                    System.out.println("invoke：MemoryMapStateBacking getTuples：next");
                    Map.Entry<List<Object>, T> e = it.next();
                    List<Object> ret = new ArrayList<Object>();
                    ret.addAll(e.getKey());
                    ret.add(((TransactionalValue)e.getValue()).getTxid());
                    return ret;
                }

                public void remove() {
                    System.out.println("invoke [MemoryMapStateBacking getTuples] 【remove】");
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            };
        }

        @Override
        public List<T> multiGet(List<List<Object>> keys) {
            logger.info(("invoke [MemoryMapStateBacking] 【multiGet】"));
            //logger.info(("invoke [MemoryMapStateBacking] 【multiGet】"+keys.toString()));
            List<T> ret = new ArrayList<T>();
            for(List<Object> key : keys){
                ret.add(db.get(key));
            }
            return ret;
        }

        @Override
        public void multiPut(List<List<Object>> keys, List<T> vals) {
            //logger.info("invoke [MemoryMapStateBacking] 【multiPut】" + keys);
            new Exception();
            for(int i = 0; i < keys.size(); i++){
                List<Object> key = keys.get(i);
                T val = (T)vals.get(i);
//                HashMap<String, ShowClickLog> sclMap = ((HashMap<String, ShowClickLog>)(((TransactionalValue)val).getVal()));
//                for(Map.Entry<String, ShowClickLog> entry : sclMap.entrySet()){
//                    logger.info("invoke [MemoryMapStateBacking] 【multiPut】" + entry.getValue().getCharge());
//                }
                try {
                    dbFile.writer(key, val);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                db.put(key, val);
            }
        }
    }

    public static class Factory implements StateFactory{
        String _id;

        public Factory() {
            _id = UUID.randomUUID().toString();
        }

        @Override
        public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
            return new CusMemoryMapState(_id + partitionIndex);
        }
    }
}
