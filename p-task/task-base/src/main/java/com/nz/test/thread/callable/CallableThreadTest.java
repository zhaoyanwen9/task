package com.nz.test.thread.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableThreadTest implements Callable<Integer> {
    public static void main(String[] args) {
        CallableThreadTest callableThreadTest = new CallableThreadTest();
        FutureTask<Integer> futureTask = new FutureTask<>(callableThreadTest);
        new Thread(futureTask, "有返回值的线程").start();
        try {
            System.out.println("子线程的返回值：" + futureTask.get());
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
    }

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
