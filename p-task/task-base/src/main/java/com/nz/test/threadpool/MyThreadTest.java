package com.nz.test.threadpool;

import org.apache.logging.log4j.message.ThreadDumpMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * https://blog.csdn.net/loulanyue_/article/details/100166717
 * https://blog.csdn.net/qq_27581243/article/details/86650682
 * https://blog.csdn.net/weixin_41888813/article/details/90769126?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.control&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.control
 */
public class MyThreadTest {

    static final String SUCCESS = "0";

    static final String FAIL = "1";

    private static SynchronousQueue<Runnable> SYNCHRONOUS_QUEUE = new SynchronousQueue<Runnable>();

    private static LinkedBlockingQueue<Runnable> LINKED_BLOCKING_QUEUE = new LinkedBlockingQueue<Runnable>(10);

    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS, LINKED_BLOCKING_QUEUE);

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {
        // ExecutorService executorService;
        // // 只有一个线程的线程池: core = max = 1, 0s, 无界LinkedBlockingQueue, 异常结束 -> 新建一个新线程继续执行任务
        // executorService = Executors.newSingleThreadExecutor();
        // for (int i = 0; i < 3; i++) {
        //     executorService.execute(new MyRunnableThread("single", i, true));
        // }
        // // 指定线程数的线程池: core = max, 无界LinkedBlockingQueue, 异常结束 -> 补充一个新线程, 0s
        // executorService = Executors.newFixedThreadPool(3);
        // for (int i = 0; i < 3; i++) {
        //     executorService.execute(new MyRunnableThread("fixed", i, true));
        // }
        // // 缓存线程的线程池: core = 0, SynchronousQueue, max = Integer.MAX_VALUE, 60s
        // executorService = Executors.newCachedThreadPool();
        // for (int i = 0; i < 3; i++) {
        //     executorService.execute(new MyRunnableThread("cached", i, true));
        // }
        // executorService.shutdown();

        /**
         * 创建一个定长线程池，支持定时及周期性任务执行。延迟执行
         */
        // ScheduledExecutorService scheduledThreadPool3 = Executors.newScheduledThreadPool(5);
        // Runnable r3 = () -> System.out.println(Thread.currentThread().getName() + ", 执行普通任务");
        // for (int i = 0; i < 3; i++) {
        //     scheduledThreadPool3.execute(r3);
        // }
        // Runnable r1 = () -> System.out.println(Thread.currentThread().getName() + ", 3秒后执行");
        // scheduledThreadPool3.schedule(r1, 3, TimeUnit.SECONDS);
        // Runnable r2 = () -> System.out.println(Thread.currentThread().getName() + ", 延迟5秒后每3秒执行一次");
        // scheduledThreadPool3.scheduleAtFixedRate(r2, 5, 3, TimeUnit.SECONDS);

        /**
         * 核心 最大 空闲时间 时间单位 队列 + 线程工厂 / 拒绝策略 -> 5 - 6 - 6 - 7
         *
         * 核心 - 阻塞队列:BlockingQueue - 非核心 - 拒绝
         *
         * 提交任务数                           临界值
         * 1.不大于核心线程数: 使用核心线程       小于等于5
         * 2.大于核心线程池数: 进入队列排队       大于5  或 小于等于25
         * 3.核心线程被占、队列满: 启用非核心线程  大于25 或 小于等于30
         * 4.达最大线程数、队列满: 拒绝策略       大于30
         */
        BlockingQueue<Runnable> bq = new ArrayBlockingQueue<Runnable>(20); // 有界
        bq = new LinkedBlockingDeque<>(); // Integer.MAX_VALUE
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(5, 10, 0, TimeUnit.MILLISECONDS, bq);
        // tpe.prestartAllCoreThreads();
        // for (int i = 0; i < 26; i++) {
        //     Runnable t1 = new MyRunnableThread("tpe", i, true);
        //     tpe.submit(t1);
        // }
        // boolean flag = true;
        // int i = 0;
        // System.out.println("#### 核心线程数=" + tpe.getCorePoolSize() + " 最大线程数=" + tpe.getMaximumPoolSize() + " 等待队列=" + bq.size() + " 线程池任务数=" + tpe.getTaskCount());
        // while (flag) {
        //     System.out.println("#### " + (++i) + ": " + simpleDateFormat.format(new Date()) + "\t运行线程数=" + tpe.getActiveCount() + " 已完成任务数=" + tpe.getCompletedTaskCount() + " 排队任务数=" + tpe.getQueue().size());
        //     Thread.sleep(2000);
        //     if (tpe.getTaskCount() == tpe.getCompletedTaskCount()) {
        //         tpe.shutdown();
        //         flag = false;
        //     }
        // }


        // core:3 queue:10 max:5
        List<Future<String>> futures = new ArrayList<>();
        int taskNum = 16;
        System.out.println("开始发送>>>>>");
        System.out.println("#### 当前线程数:" + poolExecutor.getPoolSize());
        for (int i = 0; i < taskNum; i++) {
            try {
                MyCallableThread task = new MyCallableThread("编号-" + (i >= 10 ? i : "0" + i) + ",2021马上到了,新年快乐！");
                Future<String> future = poolExecutor.submit(task);
                futures.add(future);
            } catch (Exception e) {
                System.out.println("编号: " + (i >= 10 ? i : "0" + i) + "，发送异常原因：" + e.getMessage());
            }
        }
        System.out.println("#### 当前线程数:" + poolExecutor.getPoolSize());
        poolExecutor.shutdown();
        boolean flag = true;
        while (flag) {
            if (poolExecutor.isTerminated()) {
                for (Future<String> future : futures) {
                    System.out.println(future.get());
                }
                flag = false;
            }
            TimeUnit.SECONDS.sleep(5);
        }
        System.out.println("#### 当前线程数: " + poolExecutor.getPoolSize());
    }
}
