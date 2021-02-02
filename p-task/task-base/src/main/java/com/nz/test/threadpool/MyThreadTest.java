package com.nz.test.threadpool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * https://blog.csdn.net/loulanyue_/article/details/100166717
 * https://blog.csdn.net/qq_27581243/article/details/86650682
 * https://blog.csdn.net/weixin_41888813/article/details/90769126?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.control&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.control
 */
public class MyThreadTest {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {
        // 只有一个线程的线程池: 异常结束 -> 重新创建一个新的线程继续执行任务
        // ExecutorService executorService = Executors.newSingleThreadExecutor();
        // for (int i = 0; i < 10; i++) {
        //     executorService.execute(new MyThread(i));
        // }

        // 指定线程数的线程池: 异常结束 -> 补充一个新线程
        // ExecutorService executorService = Executors.newFixedThreadPool(3);
        // for (int i = 0; i < 12; i++) {
        //     executorService.execute(new MyThread(i));
        // }

        // 缓存线程的线程池: Integer.MAX_VALUE
        // ExecutorService executorService = Executors.newCachedThreadPool();
        // for (int i = 0; i < 10; i++) {
        //     executorService.execute(new MyThread(i));
        // }

        // ScheduledThreadPoolExecutor atpe = new ScheduledThreadPoolExecutor(5); //设置线程个数
        // for (int i = 0; i < 10; i++) {
        //     int finalI = i;
        //     atpe.scheduleAtFixedRate(new Runnable() {
        //         @Override
        //         public void run() {
        //             try {
        //                 Thread.sleep(2000);
        //             } catch (InterruptedException E) {
        //                 E.printStackTrace();
        //             }
        //             System.out.println(Thread.currentThread().getName() + "打印编号：" + finalI + "======>" + simpleDateFormat.format(new Date()));//答应当前时间
        //         }
        //     }, 1000, 2000, TimeUnit.MILLISECONDS);//以固定频率重复执行线程
        // }

        // for (int i = 0; i < 10; i++) {
        //     int finalI = i;
        //     atpe.execute(new Runnable() {
        //         @Override
        //         public void run() {
        //             try {
        //                 Thread.sleep(2000);
        //             } catch (InterruptedException E) {
        //                 E.printStackTrace();
        //             }
        //             System.out.println(Thread.currentThread().getName() + "打印编号：" + finalI + "======>" + simpleDateFormat.format(new Date()));//答应当前时间
        //         }
        //     });
        // }

        // executorService.shutdown();

        /**
         * 核心 - 阻塞队列:BlockingQueue - 非核心 - 拒绝
         *
         * 提交任务数                           临界值
         * 1.不大于核心线程数: 使用核心线程       小于等于5
         * 2.大于核心线程池数: 进入队列排队       大于5  或 小于等于25
         * 3.核心线程被占、队列满: 启用非核心线程  大于25 或 小于等于30
         * 4.达最大线程数、队列满: 拒绝策略       大于30
         */
        BlockingQueue<Runnable> bq = new ArrayBlockingQueue<Runnable>(20);
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(5, 10, 0, TimeUnit.MILLISECONDS, bq);
        tpe.prestartAllCoreThreads();

        for (int i = 0; i < 26; i++) {
            Runnable t1 = new MyThread(i, true);
            tpe.submit(t1);
        }
        boolean flag = true;
        int i = 0;
        System.out.println("#### 核心线程数=" + tpe.getCorePoolSize() + " 最大线程数=" + tpe.getMaximumPoolSize() + " 等待队列=" + bq.size() + " 线程池任务数=" + tpe.getTaskCount());
        while (flag) {
            System.out.println((++i) + ": " + simpleDateFormat.format(new Date()) + "\t运行线程数=" + tpe.getActiveCount() + " 已完成任务数=" + tpe.getCompletedTaskCount() + " 排队任务数=" + tpe.getQueue().size());
            Thread.sleep(6000);
            if (tpe.getTaskCount() == tpe.getCompletedTaskCount()) {
                tpe.shutdownNow();
                flag = false;
                System.out.println("End");
            }
        }
    }
}
