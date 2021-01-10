package com.nz.test.controller;

import com.alibaba.fastjson.JSON;
import com.nz.test.service.impl.AsyncTaskServiceImpl;
import com.nz.test.service.impl.ThreadTaskService;
import com.nz.test.entity.TaskEntity;
import com.nz.test.service.impl.TaskServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * @author nz
 */
@RestController
@RequestMapping(value = "/task")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private AsyncTaskServiceImpl asyncTaskService;

    /**
     * 分页查询
     *
     * @RequestParam: 将请求参数绑定到你控制器的方法参数上, 语法为@RequestParam(value="参数名",required=true/false,defaultValue="")
     * value:参数名
     * required:是否包含该参数,默认为true
     * defaultValue:默认参数值
     */
    @RequestMapping(value = "/getByRp", method = {RequestMethod.GET})
    public String getByRp(@RequestParam(value = "page") int page,
                          @RequestParam(value = "size") int size) {
        logger.info("#### {} {}", page, size);
        return taskService.getByRp(page, size, "", 0, null, null);
    }

    /**
     * 异步调用:
     *
     * @return
     * @throws InterruptedException
     * @Async
     * @EnableAsync
     */
    @RequestMapping("/taskService")
    public String asyncTask() throws InterruptedException {
        long start = System.currentTimeMillis();
        /**
         * 一. Thread
         * 1. 新建线程并执行任务类
         * 2. jdk1.8之后可以使用Lambda 表达式
         * 二. async
         * 1.异步调用,无返回值
         * 2.异步调用,有返回值
         * 3.异步调用,无返回值,事务测试
         */
        new Thread(new ThreadTaskService()).start();
        new Thread(() -> {
            try {
                long sTime = System.currentTimeMillis();
                Thread.sleep(3000);
                long eTime = System.currentTimeMillis();
                System.out.println("当前线程：" + Thread.currentThread().getName() + "，" + "任务一耗时：" + (eTime - sTime) + "ms");
            } catch (InterruptedException e) {

            }
        }).start();
        asyncTaskService.asyncTask();
        Future<String> future = asyncTaskService.asyncTask(UUID.randomUUID().toString());
        asyncTaskService.asyncTaskForTransaction(true);
        long end = System.currentTimeMillis();
        System.out.println("任务总耗时：" + (end - start) + "ms");
        return "任务总耗时：" + (end - start) + "ms";
    }

    @RequestMapping(value = "/add", method = {RequestMethod.GET})
    public String add() {
        TaskEntity taskEntity = new TaskEntity();
        return taskService.add(taskEntity);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public String deleteById() {
        return taskService.deleteById();
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public String editById(@RequestParam("id") int id) {
        TaskEntity taskEntity = new TaskEntity();
        return taskService.editById(taskEntity);
    }

    @RequestMapping(value = "/get_all", method = {RequestMethod.GET})
    public String getAll() {
        return taskService.getAll();
    }

    @RequestMapping(value = "/start", method = {RequestMethod.GET})
    public String startById() {
        return taskService.startById();
    }

    @RequestMapping(value = "/stop", method = {RequestMethod.GET})
    public String stopById() {
        return taskService.stopById();
    }

    @RequestMapping(value = "/retry", method = {RequestMethod.GET})
    public String retry() {
        System.err.println("client 1 call ...........");
        ///client 睡眠 6s 超过了配置的响应等待3s
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "client 1";
    }

    /**
     * 查询最新任务配置
     *
     * @param name 任务名称
     * @return
     */
    @RequestMapping(value = "/getByName", method = {RequestMethod.GET})
    public List<TaskEntity> getByName(@RequestParam(value = "name", required = true) String name) {
        return taskService.getByName(name);
    }

    /**
     * 查询任务信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = {RequestMethod.GET})
    public TaskEntity getById(@PathVariable int id) {
        return taskService.getById(id);
    }

    /**
     * 根据任务id更新任务名称
     *
     * @param s
     * @return
     */
    @RequestMapping(value = "/updateNameById", method = RequestMethod.POST)
    public String updateNameById(@RequestBody String s) {
        return taskService.updateNameById(s);
    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public String save(@RequestBody String body) {
        TaskEntity taskEntity = JSON.parseObject(body, TaskEntity.class);
        String result;
        taskEntity.setName(UUID.randomUUID().toString());
        taskEntity.setStatus(0);
        taskEntity.setcTime(System.currentTimeMillis());
        taskEntity.setProgress(0);
        taskEntity.setDelete(false);
        try {
            taskService.save(taskEntity);
            result = "ok";
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }

    // @Autowired
    // private KafkaSender kafkaSender;
    // private KafkaConsumer kafkaConsumer;
    // @GetMapping(value = "/sender")
    // public String sendMsg() {
    //     return kafkaSender.send(UUID.randomUUID().toString());
    // }
    // @RequestMapping(value = "/add", method = {RequestMethod.POST})
    // public String addTask() {
    //     return UUID.randomUUID().toString();
    // }
}
