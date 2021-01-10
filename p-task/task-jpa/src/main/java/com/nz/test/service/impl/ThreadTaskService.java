package com.nz.test.service.impl;

public class ThreadTaskService implements Runnable {
    @Override
    public void run() {
        try {
            long start = System.currentTimeMillis();
            Thread.sleep(3000);
            long end = System.currentTimeMillis();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "，" + "任务一耗时：" + (end - start) + "ms");
        } catch (InterruptedException e) {

        }
    }
}
