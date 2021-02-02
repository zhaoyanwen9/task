package com.nz.test.threadpool;

import java.util.concurrent.*;

/**
 * https://www.cnblogs.com/zincredible/p/10984459.html
 * 1.四种线程池:
 * newSingleThreadExecutor - 单线程化的线程池
 * newFixedThreadPool - 定长线程池
 * newScheduledThreadPool - 可定期或者延时执行的定长线程池
 * newCachedThreadPool - 可缓存线程池
 * 2.核心类: ThreadPoolExecutor
 * 3.阻塞队列
 */
public class TestThreadPool {
    public static void main(String[] args) {
        //ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        //for (int i = 1; i <= 5; i++) {
        //    final int index = i;
        //    fixedThreadPool.execute(new Runnable() {
        //        @Override
        //        public void run() {
        //            try {
        //                System.out.println("第" + index + "个线程" + Thread.currentThread().getName());
        //                Thread.sleep(1000);
        //            } catch (InterruptedException e) {
        //                e.printStackTrace();
        //            }
        //        }
        //    });
        //}
        //fixedThreadPool.shutdown();
        //
        //ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        //scheduledThreadPool.schedule(new Runnable() {
        //    @Override
        //    public void run() {
        //        System.out.println("延迟三秒");
        //    }
        //}, 3, TimeUnit.SECONDS);
        //
        //ScheduledExecutorService scheduledThreadPool2 = Executors.newScheduledThreadPool(3);
        //scheduledThreadPool2.scheduleAtFixedRate(new Runnable() {
        //    @Override
        //    public void run() {
        //        System.out.println("延迟1秒后每三秒执行一次");
        //    }
        //}, 1, 3, TimeUnit.SECONDS);

        /**
         * 创建一个定长线程池，支持定时及周期性任务执行。延迟执行
         */
        //ScheduledExecutorService scheduledThreadPool3 = Executors.newScheduledThreadPool(5);
        //Runnable r1 = () -> System.out.println("线程名称：" + Thread.currentThread().getName() + "，执行:3秒后执行");
        //scheduledThreadPool3.schedule(r1, 3, TimeUnit.SECONDS);
        //Runnable r2 = () -> System.out.println("线程名称：" + Thread.currentThread().getName() + "，执行:延迟2秒后每3秒执行一次");
        //scheduledThreadPool3.scheduleAtFixedRate(r2, 2, 3, TimeUnit.SECONDS);
        //Runnable r3 = () -> System.out.println("线程名称：" + Thread.currentThread().getName() + "，执行:普通任务");
        //for (int i = 0; i < 5; i++) {
        //    scheduledThreadPool3.execute(r3);
        //}

        /**
         * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
         */
        // ExecutorService pool = Executors.newSingleThreadExecutor();
        // for (int i = 0; i < 10; i++) {
        //     final int ii = i;
        //     pool.execute(() -> System.out.println(Thread.currentThread().getName() + "=>" + ii));
        // }

        /**
         * 1.创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小<br>
         * 2.线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程<br>
         * 3.因为线程池大小为3，每个任务输出index后sleep 2秒，所以每两秒打印3个数字，和线程名称<br>
         */
        // ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        // for (int i = 0; i < 10; i++) {
        //     final int ii = i;
        //     fixedThreadPool.execute(() -> {
        //         System.out.println("线程名称：" + Thread.currentThread().getName() + "，执行" + ii);
        //         try {
        //             Thread.sleep(2000);
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     });
        // }

        /**
         * 1.创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程<br>
         * 2.当任务数增加时，此线程池又可以智能的添加新线程来处理任务<br>
         * 3.此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小<br>
         */
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 10; i++) {
            final int ii = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(() -> System.out.println("线程名称：" + Thread.currentThread().getName() + "，执行" + ii));
        }
    }
}
