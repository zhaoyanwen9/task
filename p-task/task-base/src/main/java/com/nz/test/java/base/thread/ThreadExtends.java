package com.nz.test.java.base.thread;

/**
 * 继承Thread类，实现多线程
 * 实现步骤：
 * 1.创建一个Thread类的子类
 * 2.在Thread的子类中重写Thread类中的run方法设置线程任务
 * 3.创建Thread的子类对象
 * 4.调用Thread类中的start方法，开启新的线程，执行run方法
 * 结果是两个线程并发地运行，当前线程（main线程）和另外一个线程（执行其run方法）
 * 多次启动一个线程是非法的，特别线程结束后，不能重新启动
 * java属于抢占式执行，哪个线程级别高就优先执行；同一个优先级随机选择
 */
public class ThreadExtends extends Thread {

    public ThreadExtends() {
    }

    /**
     * 使用父类来创建新线程名称
     *
     * @param name
     */
    public ThreadExtends(String name) {
        super(name);
    }

    /**
     * 重写run方法
     */
    @Override
    public void run() {
        //获取线程名称
        //String name = this.getName();
        //System.out.println("线程名称："+name);

        //获取当前线程名称
        System.out.println(Thread.currentThread().getName() + "执行");

        //链式编程
        //System.out.println(Thread.currentThread().getName());
    }
}
