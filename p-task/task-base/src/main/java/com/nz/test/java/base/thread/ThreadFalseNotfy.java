package com.nz.test.java.base.thread;

public class ThreadFalseNotfy {

    static class Thread1 {

        volatile int num = 3;

        /**
         * 大于5等待,否则+1然后唤醒
         * @return
         */
        public synchronized int add() {
            try {
//                if (num >= 5) {
//                    wait();
//                }
                /**
                 * 源码里面给出了建议就是在循环中使用判断
                 */
                System.out.println(Thread.currentThread().getName() + " add : " + num);
                while (num >= 5) {
                    wait();
                }
                num++;
                //notifyAll();
                notify();
                return num;

            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
            return num;
        }

        /**
         * 小于0等待,否则-1然后唤醒
         * @return
         */
        public synchronized int minus() {
            try {
//                if (num <= 0) {
//                    wait();
//                }
                /**
                 * 源码里面给出了建议就是在循环中使用判断
                 */
                while (num <= 0) {
                    wait();
                }
                System.out.println(Thread.currentThread().getName() + " minus : " + num);
                num--;
                notify();
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
            return num;
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Thread1 t1 = new Thread1();
        Thread t2 = new Thread(() -> {
            int i = 0;
            while (i++ < 50) {
                t1.add();
            }
        });
        Thread t5 = new Thread(() -> {
            int i = 0;
            while (i++ < 50) {
                t1.add();
            }
        });
        Thread t3 = new Thread(() -> {
            int i = 0;
            while (i++ < 50) {
                t1.minus();
            }
        });
        Thread t4 = new Thread(() -> {
            int i = 0;
            while (i++ < 50) {
                t1.minus();
            }
        });
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // 打印状态的线程
        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        // 1秒打印一次，不然疯狂打印，根本看不到其余的输出
                        Thread.sleep(10000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("加法线程1"+t2.getState());
                    System.out.println("加法线程2"+t3.getState());
                    System.out.println("减法线程1"+t4.getState());
                    System.out.println("减法线程2"+t5.getState());
                }
            }
        });
        t6.start();
    }
}
