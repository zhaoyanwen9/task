package com.nz.test.thread.runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyThreadRunnable implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MyThreadRunnable.class);

    private static int interN;

    private int sleep;

    static {
        logger.info("[静态代码块]");
        interN = 5;
    }

    {
        logger.info("[代码块]");
        sleep = 1000;
    }

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Map<String, Object> map = new LinkedHashMap<>(16);

    private String name;

    public MyThreadRunnable(String name) {
        logger.info("[构造方法]");
        this.name = name;
    }

    /**
     * 运行状态
     * 阻塞状态: 如果调用 sleep(),suspend(),wait() 等方法，线程都将进入阻塞状态,发生阻塞时线程不能进入排队队列，只有当引起阻塞的原因被消除后，线程才可以转入就绪状态
     * 死亡状态: 线程调用 stop() 方法时或 run() 方法执行结束后，即处于死亡状态
     */
    @Override
    public void run() {
        String key, value;
        Date sDate, eDate;
        String fSDate, fEdate;
        try {
            for (int i = 0; i < 10; i++) {
                sDate = new Date();
                fSDate = simpleDateFormat.format(sDate);
                // 线程休眠
                if (i == interN) {
                    logger.info("[开始休眠5秒后线程中断]");
                    while (interN > 0) {
                        Thread.sleep(sleep);
                        logger.info("{} {}", interN--, simpleDateFormat2.format(new Date()));
                    }
                    // 中断此线程,但实际上只是给线程设置一个中断标志，线程仍会继续运行
                    Thread.currentThread().interrupt();
                } else {
                    if (i == 0) {
                        Thread.yield();
                    }
                    Thread.sleep(sleep);
                }
                eDate = new Date();
                fEdate = simpleDateFormat.format(eDate);
                key = name + "-" + i + "-" + Thread.currentThread().hashCode();
                value = fSDate + " to " + fEdate;
                map.put(key, value);
                logger.info("{} {}", key, value);
            }
        } catch (InterruptedException e) {
            logger.info("[休眠终止] {}", simpleDateFormat2.format(new Date()));
        }
        logger.info("[运行日志]:");
        for (String x : map.keySet()) {
            logger.info("\t{} : {}", x, map.get(x));
        }
    }
}
