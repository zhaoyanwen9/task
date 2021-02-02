package com.nz.test.thread.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyThreadThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(MyThreadThread.class);

    private String name;

    private int ticket = 5;

    public MyThreadThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            // 同步代码块
            synchronized (this) {
                if (ticket > 0) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                    }
                    logger.info("{} {} {} 卖票: ticket = {}", Thread.currentThread().getName(), name, i, ticket--);
                } else {
                    logger.info("{} {} {} 售罄: ticket = {}", Thread.currentThread().getName(), name, i, ticket);
                }
            }
            //this.saleTicket(i);
        }
    }

    /**
     * 同步方法
     *
     * @param i
     */
    public synchronized void saleTicket(int i) {
        if (ticket > 0) {
            logger.info("{} {} {} 卖票: ticket = {}", Thread.currentThread().getName(), name, i, ticket--);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        } else {
            logger.info("{} {} {} 售罄: ticket = {}", Thread.currentThread().getName(), name, i, ticket);
        }
    }
}
