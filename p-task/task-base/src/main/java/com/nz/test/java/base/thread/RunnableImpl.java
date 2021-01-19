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
    }

    @Override
    public void run() {
        int i = 0;
        while (i++ < 5) {
            System.out.println(Thread.currentThread().getName() + "执行: " + "i = " + i);
        }
    }
}
