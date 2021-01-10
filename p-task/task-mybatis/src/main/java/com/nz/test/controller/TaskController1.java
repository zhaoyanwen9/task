package com.nz.test.controller;

import com.nz.test.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/task")
public class TaskController1 {

    /**
     * @Autowired 与 @Resource的区别
     * @Autowired 类型注入
     * @Resource Bean名称注入, 其次按照类型搜索
     * @Autowired 、 @Qualifie("beanName") 结合根据名字和类型注入
     */
    @Autowired
    private TaskService taskService1;

    @Resource(name = "taskService", type = TaskService.class)
    private TaskService taskService2;

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/getByRp")
    public String getByRp(@RequestParam(value = "page") int page,
                          @RequestParam(value = "size") int size) {
        return taskService1.getByRp(page, size);
    }

    /**
     * 根据名称模糊匹配
     *
     * @param name
     * @return
     */
    @GetMapping(value = "/getByName")
    public String getByName(@RequestParam(value = "name") String name) {
        return taskService1.getByName(name);
    }

    /**
     * 获取任务数量
     *
     * @return 任务数量
     */
    @GetMapping(value = "/getCount")
    public int getCount() {
        return taskService1.getCount();
    }

    /**
     * 获取任务关键信息
     *
     * @param pkId
     * @return
     */
    @GetMapping(value = "/getCruxInfo")
    public Map<String, Object> getCruxInfo(@RequestParam(value = "id") int pkId) {
        return taskService1.getCruxInfo(pkId);
    }

    /**
     * 根据主键获取任务信息
     *
     * @param pkId 主键ID
     * @return 任务信息
     */
    @GetMapping(value = "/getById/{id}")
    public String getById(@PathVariable(value = "id") int pkId) {
        // 缓存对象1
        System.out.println("================缓存对象 1=================");
        taskService1.getById(pkId);
        // 缓存对象2
        System.out.println("========缓存对象2,剔除缓存中的对象 1=======");
        taskService1.getById(pkId + 1);
        // 读取对象1
        System.out.println("==========对象1缓存被剔除,执行查询sql===========");
        taskService1.getById(pkId);
        //暂停 5s
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
        // 读取对象2
        System.out.println("============5s 后再次查询对象 2=============");
        taskService1.getById(pkId + 1);
        return taskService1.getById(pkId);
    }

    /**
     * 获取所有
     *
     * @return
     */
    @GetMapping(value = "/getAll")
    public List<Map<String, Object>> getAll() {
        return taskService1.getAll();
    }

    /**
     * 获取多条
     *
     * @param pkId
     * @return
     */
    @GetMapping(value = "/getMany")
    public List<Map<String, Object>> getMany(@RequestParam(value = "id") int pkId) {
        return taskService1.getMany(pkId);
    }
}
