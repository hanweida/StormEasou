/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easou.let.fun;

import backtype.storm.task.IMetricsContext;
import backtype.storm.tuple.Values;
import storm.trident.state.ITupleCollection;
import storm.trident.state.OpaqueValue;
import storm.trident.state.State;
import storm.trident.state.StateFactory;
import storm.trident.state.ValueUpdater;
import storm.trident.state.map.IBackingMap;
import storm.trident.state.map.MapState;
import storm.trident.state.map.OpaqueMap;
import storm.trident.state.map.RemovableMapState;
import storm.trident.state.map.SnapshottableMap;
import storm.trident.state.snapshot.Snapshottable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryMapStates<T> implements
        Snapshottable<T>,
        MapState<T>{

    MemoryMapStateBacking<OpaqueValue> _backing; //支持
    SnapshottableMap<T> _delegate;//代表
    List<List<Object>> _removed = new ArrayList();
    Long _currTx = null;

    public MemoryMapStates(String id) {
        System.out.println("invoke：MemoryMapState "+id);
        _backing = new MemoryMapStateBacking(id);
        _delegate = new SnapshottableMap(OpaqueMap.build(_backing), new Values("$MEMORY-MAP-STATE-GLOBAL$"));
    }

    public T update(ValueUpdater updater) {
        System.out.println("invoke Snapshottable ：update");
        return _delegate.update(updater);
    }

    public void set(T o) {
        System.out.println("invoke Snapshottable ：set");
        _delegate.set(o);
    }

    public T get() {
        System.out.println("invoke Snapshottable ReadOnlySnapshottable：get");
        return _delegate.get();
    }

    public void beginCommit(Long txid) {
        System.out.println("invoke State ：beginCommit" + txid);
        System.out.println("invoke State ：beginCommit" + _currTx);
        _delegate.beginCommit(txid);
        if(txid==null || !txid.equals(_currTx)) {
            System.out.println("beginCommit :  _backing.multiRemove");
            _backing.multiRemove(_removed);
        }
        _removed = new ArrayList();
        _currTx = txid;
        System.out.println("beginCommit "+_currTx);
    }

    public void commit(Long txid) {
        System.out.println("invoke State ：commit");
        _delegate.commit(txid);
    }

//    public Iterator<List<Object>> getTuples() {
//        System.out.println("invoke ITupleCollection ：getTuples");
//        return _backing.getTuples();
//    }

    public List<T> multiUpdate(List<List<Object>> keys, List<ValueUpdater> updaters) {
        System.out.print("invoke MapState ：multiUpdate");
//        if(keys.get(0).get(0).equals("the")){
//            System.out.print(" keys："+keys.toString());
//        }
        System.out.print(" keys："+keys.toString());
        for(ValueUpdater valueUpdater : updaters){
            System.out.println(" updaters："+ valueUpdater.toString());
        }
        return _delegate.multiUpdate(keys, updaters);
    }

    public void multiPut(List<List<Object>> keys, List<T> vals) {
        System.out.println("invoke MapState ：multiPut" + keys.toString());
        _delegate.multiPut(keys, vals);
    }

    public List<T> multiGet(List<List<Object>> keys) {
        System.out.println("invoke MapState ReadOnlyMapState ：multiGet" + keys.toString());
        return _delegate.multiGet(keys);
    }

//    @Override
//    public void multiRemove(List<List<Object>> keys) {
//        System.out.println("invoke RemovableMapState ：multiRemove");
//        List nulls = new ArrayList();
//        for(int i=0; i<keys.size(); i++) {
//            nulls.add(null);
//        }
//        // first just set the keys to null, then flag to remove them at beginning of next commit when we know the current and last value are both null
//        multiPut(keys, nulls);
//        _removed.addAll(keys);
//    }

    public static class Factory implements StateFactory {

        String _id;

        public Factory() {
            _id = UUID.randomUUID().toString();
        }

        @Override
        public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
            System.out.println("invoke：makeState");
            return new MemoryMapStates(_id + partitionIndex);
        }
    }

    static ConcurrentHashMap<String, Map<List<Object>, Object>> _dbs = new ConcurrentHashMap<String, Map<List<Object>, Object>>();
    static class MemoryMapStateBacking<T> implements IBackingMap<T>, ITupleCollection {


        Map<List<Object>, T> db;
        Long currTx;

        public MemoryMapStateBacking(String id) {
            System.out.println("invoke：MemoryMapStateBacking");
            if (!_dbs.containsKey(id)) {
                _dbs.put(id, new HashMap());
            }
            this.db = (Map<List<Object>, T>) _dbs.get(id);
        }

        //被调用
        public void multiRemove(List<List<Object>> keys) {
            System.out.println("invoke：MemoryMapStateBacking：multiRemove");
            for(List<Object> key: keys) {
                db.remove(key);
            }
        }

        //被调用
        public void multiUpdate(List<List<Object>> keys, List<ValueUpdater> updaters) {
            System.out.println("invoke：MemoryMapStateBacking：multiUpdate");
            for(List<Object> key: keys) {
                db.remove(key);
            }
        }

        @Override
        public List<T> multiGet(List<List<Object>> keys) {
            System.out.println("invoke：MemoryMapStateBacking：multiGet" + keys.toString());
            List<T> ret = new ArrayList();
            for (List<Object> key : keys) {
                ret.add(db.get(key));
            }
            return ret;
        }

        @Override
        public void multiPut(List<List<Object>> keys, List<T> vals) {
            System.out.println("invoke：MemoryMapStateBacking：multiPut keys："+keys.toString());
            for (int i = 0; i < keys.size(); i++) {
                List<Object> key = keys.get(i);
                T val = vals.get(i);
                db.put(key, val);
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
                    System.out.println("invoke：MemoryMapStateBacking：next");
                    Map.Entry<List<Object>, T> e = it.next();
                    List<Object> ret = new ArrayList<Object>();
                    ret.addAll(e.getKey());
                    ret.add(((OpaqueValue)e.getValue()).getCurr());
                    return ret;
                }

                public void remove() {
                    System.out.println("invoke：MemoryMapStateBacking：remove");
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            };
        }
    }
}
