package com.nz.test.java.base.thread;

/**
 * Thread继承与Runnable接口实现的区别
 * 实现Runnable接口创建多线程的好处：
 * 1.避免了单继承的局限性
 * 2.增强了程序的扩展性，降低了耦合性(解耦)，eg：多个Runnable的实现类可以有不同的线程任务
 * 3.多个Thread对象，共用一个Runnable实现类对象的资源
 */

public class MyThread {
    public static void main(String[] args) throws InterruptedException {
        /**
         *  01.设置线程名称
         *      A.创建Thread类的子类对象,设置线程名
         *      B.通过构造方法用super(name)父类来创建线程名称
         *  02.启动线程
         *      start():多线程,开辟新的栈空间，新线程
         *      run():单线程,由main方法来执行
         */

        ThreadExtends anThread = new ThreadExtends();
        anThread.setName("A");
        anThread.start();

        new ThreadExtends("B").start();

        for (int i = 0; i < 5; i++) {
            Thread.sleep(100);
            System.out.println("y主线程" + Thread.currentThread().getName() + ": " + i);
        }

        // 1.创建
        RunnableImpl runnableThread = new RunnableImpl();
        // 2.多个Thread共用一个runnableThread资源
        Thread t1 = new Thread(runnableThread, "C");
        Thread t2 = new Thread(runnableThread, "D");
        // 3.启动
        t1.start();  //新线程开启
        t2.start();  //新线程开启
    }
}
