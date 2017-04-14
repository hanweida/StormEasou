package com.easou.let.pojo;

import com.easou.let.utils.SimpleDateUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-12-5
 * Time: 下午6:48
 * To change this template use File | Settings | File Templates.
 */
public class TaskQueue<T extends Object> extends ArrayList {

    AtomicInteger count = new AtomicInteger(0);

    public void addItem(T item){
        add(item);
    }

    public T popItem(){
        int i = count.getAndIncrement();
        return i < size() ? (T)get(i) : null;
    }

    public static void main(String[] args) throws InterruptedException {
        while (true){
            long startTime = System.nanoTime();
            System.out.println("start time ：" + SimpleDateUtils.getCurrentTime());
            List<ShowClickLog> showClickLogList = new ArrayList<ShowClickLog>();
            for(int i = 0 ; i < 100 ; i++){
                ShowClickLog showClickLog = new ShowClickLog();
                showClickLog.setClick(i);
                showClickLogList.add(showClickLog);
            }

            final TaskQueue<ShowClickLog> taskQueue = new TaskQueue<ShowClickLog>();
            Iterator iterator = showClickLogList.iterator();
            while (iterator.hasNext()){
                taskQueue.addItem((ShowClickLog)iterator.next());
            }

            List<Thread> threadList = new ArrayList<Thread>();
            for(int i = 0; i< 4 ;i++){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                       /* int i = B.count.get();
                        System.out.println(Thread.currentThread().getName() + "：" + list.get(i) + "：" + i);
                        i = B.count.getAndIncrement();
                        if(i == 9){
                            break;
                        }*/
                            ShowClickLog showClickLog9 = taskQueue.popItem();
                            if(null == showClickLog9){
                                break;
                            } else {
                                System.out.println(Thread.currentThread().getName() +"："+showClickLog9.getClick());
                            }
                        }

                    }
                });
                threadList.add(thread);
                thread.start();
            }
            for(Thread thread : threadList){
                thread.join();
            }
            System.out.println("start time ：" + SimpleDateUtils.getCurrentTime());
            Thread.sleep(10000);
        }

    }
}
