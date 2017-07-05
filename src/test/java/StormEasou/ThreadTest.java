package StormEasou;

import com.easou.let.pojo.ShowClickLog;
import com.easou.let.pojo.TaskQueue;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ES-BF-IT-126 on 2017/6/26.
 */
public class ThreadTest {
    private Boolean flag = false;
    private ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap();
    private ConcurrentHashMap<String, Integer> cachMap = new ConcurrentHashMap();
    private TaskQueue<Integer> taskQueue = new TaskQueue<Integer>();
    private Lock map_lock = new ReentrantLock();
    private Lock cachmap_lock = new ReentrantLock();
    //private Map<String, Integer> map = new HashMap();
    //private Map<String, Integer> cachMap = new HashMap();
    AtomicBoolean aBoolean = new AtomicBoolean(false);
    LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
    private boolean excute = true;
    @Test
    public void excute(){
        Timer timer = new Timer();
        timer.schedule(new saveTimer(), new Date(), 100);
        for(int i = 1; i < 200;i++){
//            int j = i;
//            if(i % 100 == 0){
//                j = i / 100;
//            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            saveData(i);
        }
    }

        public void saveData(int value){
            try {
                map.put(value, value);
                linkedBlockingQueue.put(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    //        if(!flag){
    //            map.put("Map", value);
    //            System.out.println("saveData Map_ "+value);
    //        }
    //        //System.out.println(Thread.currentThread().getName()+"：saveData " +flag);
    //        else {
    //            cachMap.put("CachMap", value);
    //            System.out.println("saveData CachMap_ "+value);
    //        }
        }

    public class saveTimer extends TimerTask{

        public void run() {
            for(Map.Entry<Integer, Integer> entry : map.entrySet()){
                System.out.println(entry.getKey()+"："+entry.getValue());
                map.remove(entry.getKey());
            }
//            flag = true;
//            //System.out.println(Thread.currentThread().getName()+"：saveTimer " + flag);
//            System.out.println("saveTimer-" + flag);
//            if(map.size() > 0){
//                System.out.println("Map Size" + map.size());
//                for(Map.Entry<String, Integer> entry : map.entrySet()){
//                    taskQueue.addItem(entry.getValue());
//                    System.out.println(entry.getKey()+"："+entry.getValue());
//                }
//                map.clear();
//            }
//
//            if(cachMap.size() > 0){
//                System.out.println("CachMap Size" + cachMap.size());
//                for(Map.Entry<String, Integer> entry : cachMap.entrySet()){
//                    taskQueue.addItem(entry.getValue());
//                    System.out.println(entry.getKey()+"："+entry.getValue());
//                }
//                cachMap.clear();
//            }
//
//            System.out.println("taskQueue Size【"+taskQueue.size()+"】");
//            flag = false;
       }
    }
}
