package com.nz.test.java.base.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadWaitAndNotify {

    static class Printer {

        public void Print(String str) {
            synchronized (this) {
                System.out.println(str);
                try {
                    System.out.println(Thread.currentThread().getName() + ": " + str + " and enter wait state!");
                    wait();         //进入无限等待状态
                    //等待被唤醒后，继续执行
                    int i = 0;
                    // while(true)
                    while (i++ < 3) {
                        Thread.sleep(1000);
                        System.out.println(str);
                    }
                } catch (InterruptedException e) {
                    System.out.println(e.toString());   //打印中断异常，并停止线程
                }
            }
        }

        public void wakeup() {
            synchronized (this) {
                // notify();   // 唤醒该线程,仅仅唤醒了一条线程

                notifyAll(); // 将所有进入了等待状态的线程都唤醒了。
                System.out.println("wakeup: " + Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        Printer p = new Printer();
        Runnable r1 = () -> p.Print("this is r1!");
        Runnable r2 = () -> p.Print("this is r2!");
        Runnable r3 = () -> p.Print("this is r3!");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(30));
//        executor.execute(r1);
//        executor.execute(r2);
//        executor.execute(r3);
        executor.submit(r1);
        executor.submit(r2);
        executor.submit(r3);

        try {
            Thread.sleep(2000);
            p.wakeup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
