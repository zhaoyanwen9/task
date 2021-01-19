package com.nz.test.java.base.thread;

/**
 * 无法获取对象锁，线程被阻塞
 */
public class ThreadBlocked {

    static class Thread1 extends Thread {

        private static final Object lock = 0;  // 由于以同一个线程类来创建线程实例，所以使用静态对象来提供对象锁

        String str = "";

        public Thread1(String str) {
            this.str = str;
        }

        @Override
        public void run() {
            int i = 0;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Print(str);
        }

        public void Print(String str) {
            synchronized (lock) {
                int i = 0;
                /**
                 * 由于synchronized关键字会给Print方法加上对象锁，进入之后会进入死循环，一直打印，不会释放掉锁。
                 * 所以线程2会一直等待该锁的资源，状态一直显示为BLOCKED，而已经获取锁对象的线程1则一直可以执行，之所以显示的是TIMED_WAITING，是为了降低输出速度，在代码里设置了打印一次休眠一秒钟，在休眠是打印状态，自然对应的是TIMED_WAITING,而不是RUNABLE。
                 * 注：这里有一个知识点，sleep方法会让出CPU的时间片资源，但是不会释放掉获取的锁，wait方法则是让出CPU时间片资源并且释放掉对象的锁。
                 *
                 * 现在要解决的问题是，如何让线程2也能执行呢？
                 *  1.等待线程1执行完毕后释放锁，线程2即可继续执行: int i=0; while(i++<10){}
                 *  2.中断线程1的执行，释放锁，让位给线程2执行
                 * 方法1是一个很平常的顺序等待执行逻辑，着重点在于让执行中的线程中断，让位给其他线程的情况。
                 */
                while (true) {
                    System.out.println("------------->" + Thread.currentThread().getName() + ": " + Thread.currentThread().getState() + " - " + str);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread1 t1 = new Thread1("1");
        Thread1 t2 = new Thread1("2");
        t1.setName("线程一");
        t1.start();
        try {
            // 同时启动线程并不能保证线程1先进入运行状态，所以暂停1s保证线程1比线程2先进入运行状态
            Thread.sleep(1000);
            t2.setName("线程二");
            t2.start();
            while (true) {
                Thread.sleep(1000);
                System.out.println("线程一 :" + t1.getState());
                System.out.println("线程二 :" + t2.getState());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
