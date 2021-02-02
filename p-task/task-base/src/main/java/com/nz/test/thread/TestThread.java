package com.nz.test.thread;

import com.nz.test.thread.runnable.MyThreadRunnable;
import com.nz.test.thread.thread.MyThreadThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * https://www.cnblogs.com/java1024/archive/2019/11/28/11950129.html
 * 任何线程一般具有5种状态,即创建、就绪、运行、阻塞、终止
 * 1.新建: Thread thread = new Thread()
 * 2.就绪: 调用线程start()方法就可以启动线程线程进入就绪状态,已经具备了运行条件,线程将进入线程队列排队
 * 3.运行: 就绪状态被调用并获得处理器资源时,线程就进入了运行状态,自动调用该线程对象的run()方法
 * 4.阻塞: 在可执行状态下,如果调用sleep(),suspend(),wait()等方法,线程都将进入阻塞状态,发生阻塞时线程不能进入排队队列,只有当引起阻塞的原因被消除后,线程才可以转入就绪状态
 * 5.死亡: 线程调用 stop()方法时或 run()方法执行结束后,即处于死亡状态
 * <p>
 * Java程序每次运行至少启动几个线程?
 * 至少会启动两个线程,一个是main线程,另外一个是JVM垃圾收集线程
 * <p>
 * 后台线程: setDaemon(),run()方法中是死循环的方式,但是程序依然可以执行完
 * 强制运行: join()方法让一个线程强制运行,线程强制运行期间,其他线程无法运行,必须等待此线程完成之后才可以继续执行
 * 线程的休眠: Thread.sleep()
 * 中断线程: 通过interrupt()方法中断其运行状态
 */
public class TestThread {

    private static final Logger logger = LoggerFactory.getLogger(TestThread.class);

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        /**
         * extends Thread
         */
        MyThreadThread myThreadThread1 = new MyThreadThread("赵言文");
        MyThreadThread myThreadThread2 = new MyThreadThread("牛密婷");
        MyThreadThread myThreadThread3 = new MyThreadThread("赵明泽");
        // 创建状态: 在程序中用构造方法创建了一个线程对象后，新的线程对象便处于新建状态
        Thread thread1 = new Thread(myThreadThread1, "线程1");
        Thread thread2 = new Thread(myThreadThread2, "线程2");
        Thread thread3 = new Thread(myThreadThread3, "线程3");
        // 线程的优先级: 最低 最高 最中等, 并非优先级越高就一定会先执行,哪个线程先执行将由 CPU 的调度决定
        thread1.setPriority(Thread.MIN_PRIORITY);
        thread2.setPriority(Thread.MAX_PRIORITY);
        thread3.setPriority(Thread.NORM_PRIORITY);
        // 就绪状态: 当线程启动时，线程进入就绪状态
        thread1.start();
        thread2.start();
        thread3.start();

        // MyThreadThread myThreadThread4 = new MyThreadThread("赵言文");
        // Thread t1 = new Thread(myThreadThread4);
        // Thread t2 = new Thread(myThreadThread4);
        // Thread t3 = new Thread(myThreadThread4);
        // t1.start();
        // t2.start();
        // t3.start();

        /**
         * implements Runnable
         */
        // MyThreadRunnable myThreadRunnable1 = new MyThreadRunnable("赵明泽");
        // Thread thread = new Thread(myThreadRunnable1, "线程1");
        // thread.start();
        // for (int i = 0; i < 5; i++) {
        //     if (i == 3) {
        //         try {
        //             thread.join(3000);
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }
        //     logger.info(Thread.currentThread().getName().toUpperCase() + "线程 -> " + simpleDateFormat.format(new Date()) + " " + i);
        // }
    }
}
