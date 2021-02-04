package com.nz.test.threadpool;


import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class MyCallableThread implements Callable<String>{
    private String message;

    public MyCallableThread(String message){
        this.message = message;
    }

    @Override
    public String call() throws Exception {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(5));
            System.out.println(Thread.currentThread().getName() + "发送短信，内容：" + message);
            return MyThreadTest.SUCCESS;
        } catch (Exception e){
            e.printStackTrace();
        }
        return MyThreadTest.FAIL;
    }
}
