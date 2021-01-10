package com.nz.test.service;

import java.util.concurrent.Future;

public interface IAsyncTaskService {

    /**
     * 异步调用,无返回值
     */
    void asyncTask();

    /**
     * 异步调用,有返回值
     */
    Future<String> asyncTask(String s);

    /**
     * 异步调用,无返回值,事务测试
     */
    void asyncTaskForTransaction(boolean flag);
}
