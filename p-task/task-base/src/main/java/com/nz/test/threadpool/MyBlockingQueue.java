package com.nz.test.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class MyBlockingQueue {

    private static final Logger logger = LoggerFactory.getLogger(MyBlockingQueue.class);

    private List queue = new LinkedList();

    // 队列容量
    private int limit = 10;

    public MyBlockingQueue(int limit) {
        this.limit = limit;
    }

    /**
     * 入队，加入一个元素，如果满了，则等待，未满则加入元素
     *
     * @param item
     * @throws InterruptedException
     */
    public synchronized void enqueue(Object item) throws InterruptedException {
        // 如果满了，则等待
        while (this.queue.size() == this.limit) {
            logger.info("入队: 队列满,wait()...");
            wait();
        }
        // 队列个数为0时,可能会有线程因为出队操作导致等待，因此需要唤醒
        if (this.queue.size() == 0) {
            logger.info("入队: 队列长度0,唤醒notifyAll()");
            notifyAll();
        }
        // 未满，添加元素
        logger.info("入队: 队列未满,add()");
        this.queue.add(item);
    }

    /**
     * 出队，如果队列为空，则等待，否则出队一个元素
     *
     * @return
     * @throws InterruptedException
     */
    public synchronized Object dequeue() throws InterruptedException {
        // 此时队列为空，因此等待
        while (this.queue.size() == 0) {
            logger.info("出队: 队列空,等待...");
            wait();
        }
        // 理由类似，唤醒等待线程
        if (this.queue.size() == this.limit) {
            logger.info("出队: 队列满,唤醒");
            notifyAll();
        }
        // 出队并返回
        logger.info("出队: remove()");
        return this.queue.remove(0);
    }

    public static void main(String[] args) throws Exception {
        MyBlockingQueue blockingQueue = new MyBlockingQueue(10);
        for (int i = 0; i < 15; i++) {
            if (i == 5) {
                blockingQueue.dequeue();
                blockingQueue.dequeue();
                blockingQueue.dequeue();
            }
            blockingQueue.enqueue(i);
        }
    }
}
