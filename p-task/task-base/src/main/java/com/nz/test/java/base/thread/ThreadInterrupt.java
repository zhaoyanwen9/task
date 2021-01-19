package com.nz.test.java.base.thread;

public class ThreadInterrupt {

    static class Thread1 extends Thread {

        static final Object lock = 0;  //由于以同一个线程类来创建线程实例，所以使用静态对象来提供对象锁
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
            synchronized (lock) {
                while (true) {
                    System.out.println("------------->" + Thread.currentThread().getName() + ": " + Thread.currentThread().getState() + " - " + str);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("[Exception] " + Thread.currentThread().getName() + "线程中断让位给其他线程" + e.toString());   //打印中断异常，并停止线程
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread1 t1 = new Thread1("线程1");
        Thread1 t2 = new Thread1("线程2");
        t1.start();
        try {
            //同时启动线程并不能保证线程1先进入运行状态，所以暂停1s保证线程1比线程2先进入运行状态
            Thread.sleep(1000);
            t2.start();
            int i = 0;
            while (true) {
                //打印两次之后再中断线程1
                if (i++ > 2) {
                    t1.interrupt();
                    i = -100000000;
                }
                Thread.sleep(1000);
                System.out.println("t1 state:" + t1.getState());
                System.out.println("t2 state:" + t2.getState());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
