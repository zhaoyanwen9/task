package com.nz.test.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nz.test.entity.Task;
import com.nz.test.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping(value = "/rest_task")
public class TaskRestController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Resource(name = "simpleTaskServiceImpl")
    private TaskService taskService;

    @GetMapping(value = "/byName")
    public List<Task> getByName(String name) {
        return taskService.getByName(name);
    }

    @GetMapping(value = "/byNameStatus")
    public List<Task> getByNameAndStatus(String name, int status) {
        return taskService.getByNameAndStatus(name, status);
    }

    @GetMapping(value = "/{id}")
    public Task getById(@PathVariable int id) {
        return taskService.getById(id);
    }

    @GetMapping(value = "/selectById")
    public HashMap selectById(HttpServletRequest request, @RequestParam(value = "id", required = false, defaultValue = "1") int id) {
        HttpSession session = request.getSession();
        logger.info("{}", session.getId());
        return taskService.selectById(id);
    }

    @PostMapping(value = "/saveOneTask")
    public int saveOneTask(@RequestParam(value = "name", required = false, defaultValue = "1") String name) {
        return taskService.saveOneTask(name);
    }

    @PostMapping(value = "/saveManyTask")
    public int saveManyTask(@RequestBody String body) {
        List<Task> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            task.setName(UUID.randomUUID().toString());
            list.add(task);
        }
        return taskService.saveManyTask(list);
    }

    @PutMapping(value = "/upgById")
    public int updateTaskById(@RequestParam(value = "id") int id,
                              @RequestParam(value = "name") String name) {
        return taskService.updateTaskById(id, name);
    }

    @DeleteMapping(value = "/delById")
    public int deleteTaskById(@RequestParam(value = "id") int id) {
        return taskService.deleteTaskById(id);
    }
}
