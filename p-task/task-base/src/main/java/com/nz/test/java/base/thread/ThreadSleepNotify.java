package com.nz.test.java.base.thread;

public class ThreadSleepNotify {
    static class Thread1 extends Thread {

        static final Object lock = new Object();  //由于以同一个线程类来创建线程实例，所以使用静态对象来提供对象锁
        String str = "";

        public Thread1(String str) {
            this.str = str;
        }

        @Override
        public void run() {
            int i = 0;
            Print(str);
        }

        public void Print(String str) {
            System.out.println(str);
            try {
                sleep(10000000);    //沉睡超长时间，模拟永远沉睡
                System.out.println("沉睡被打断之后，并不会执行sleep之后的代码!");
            } catch (InterruptedException e) {
                System.out.println(e.toString());   //打印中断异常，并停止线程
            }
            System.out.println("但是会执行异常捕捉模块和捕捉代码块之后的代码!");

        }


    }

    public static void main(String[] args) {
        Thread1 t1 = new Thread1("线程1");
        t1.start();
        try {
            Thread.sleep(1000);
            int i = 0;
            while (true) {
                System.out.println("t1 state:" + t1.getState());
                //打印两次之后再唤醒线程1
                if (i++ > 2) {
                    t1.interrupt();
                    i = -100000000;
                }
                Thread.sleep(1000);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
