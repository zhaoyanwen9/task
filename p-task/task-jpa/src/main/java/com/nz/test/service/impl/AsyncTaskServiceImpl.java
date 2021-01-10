package com.nz.test.service.impl;

import com.nz.test.dao.task.ITaskDaoJpaRepository;
import com.nz.test.entity.TaskEntity;
import com.nz.test.service.IAsyncTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.Future;

/**
 * Spring 3.x 后 @Async
 */
@Service
public class AsyncTaskServiceImpl implements IAsyncTaskService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskServiceImpl.class);

    @Autowired
    private ITaskDaoJpaRepository taskDaoRepository;

    @Async
    @Override
    public void asyncTask() {
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        System.out.println("当前线程：" + Thread.currentThread().getName() + "，" + "任务一耗时：" + (end - start) + "ms");
    }

    @Async(value = "asyncTaskExecutor")
    @Override
    public Future<String> asyncTask(String s) {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
        long end = System.currentTimeMillis();
        System.out.println("当前线程：" + Thread.currentThread().getName() + "，" + "任务二耗时：" + (end - start) + "ms");
        return AsyncResult.forValue(s);
    }

    @Async(value = "asyncTaskExecutor")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void asyncTaskForTransaction(boolean flag) {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
        long end = System.currentTimeMillis();
        System.out.println("当前线程：" + Thread.currentThread().getName() + "，" + "任务三耗时：" + (end - start) + "ms");
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName("async-" + UUID.randomUUID().toString());
        try {
            taskDaoRepository.save(taskEntity);
            if (flag) {
                throw new RuntimeException("模拟异常");
            }
        } catch (Exception e) {
            logger.info("#### {}", e.getMessage());
        }
    }
}
