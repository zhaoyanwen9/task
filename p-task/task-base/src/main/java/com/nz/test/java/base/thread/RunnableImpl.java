package com.nz.test.java.base.thread;

import java.util.List;

/**
 * 实现Runnable必须重写run()方法，一般推荐用Runnable实现
 */
public class RunnableImpl implements Runnable {

    private List<Integer> list;

    public RunnableImpl() {
    }

    public RunnableImpl(List<Integer> list) {
        this.list = list;
        System.out.println("初始化: " + this.list.hashCode());
    }

    @Override
    public void run() {
        System.out.println("#### " + Thread.currentThread().getName() + "-" + list.size() + " : " + this.list.hashCode());
        int i = 0;
        while (i++ < 10) {
            this.list.add(i);
            System.out.println(i + ": " + list + "  " + Thread.currentThread().getName() + "执行: " + "i = " + i + " : " + this.list.size());
        }
    }
}
