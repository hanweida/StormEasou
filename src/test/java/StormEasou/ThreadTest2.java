package StormEasou;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ES-BF-IT-126 on 2017/6/27.
 */
public class ThreadTest2 {
    private Boolean flag = false;
    @Test
    public void test(){
        Thread t1 = new Thread(new Run1());
        Thread t2 = new Thread(new Run2());
        t1.start();
        t2.start();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        while (true){
//
//        }
    }
    class Run1 implements Runnable {

        public void run() {
            for(int i  = 0 ;i < 10000; i++) {
                if(flag){
                    System.out.println("Main");
                } else {
                    System.out.println("Main NOT");
                }
            }
        }
    }
    class Run2 implements Runnable {

        public void run() {
                synchronized (flag){
                    for(int i  = 0 ;i < 10000; i++){
                    flag = true;
                    System.out.println("TimerTaskExcutor");
                }
            }

        }
    }

    class TimerTaskExcutor  extends TimerTask{

        public void run() {

                //synchronized (flag){

            //}
        }
    }
}
