package com.nz.test.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nz.test.entity.Task;
import com.nz.test.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author nz
 * @RestController = @ResponseBody + @Controller
 */
@Controller
@ResponseBody
@RequestMapping(value = "/task")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Resource(name = "simpleTaskServiceImpl")
    private TaskService taskService;

    @RequestMapping(value = "/getByRp", method = RequestMethod.GET)
    public List<Task> getByRp(@RequestParam(value = "page") int page,
                              @RequestParam(value = "size") int size) {
        Task task = new Task();
        IPage<Task> iPage = taskService.getByRp(task, page, size);
        return iPage.getRecords();
    }

    @RequestMapping(value = "/byName", method = RequestMethod.GET)
    public List<Task> getByName(String name) {
        return taskService.getByName(name);
    }

    @RequestMapping(value = "/byNameStatus", method = RequestMethod.GET)
    public List<Task> getByNameAndStatus(String name, int status) {
        return taskService.getByNameAndStatus(name, status);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Map<String, Object> getById(@PathVariable int id) {
        Map<String, Object> map = (Map<String, Object>) taskService.getById(id);
        return map;
    }

    @RequestMapping(value = "/selectById", method = RequestMethod.GET)
    public HashMap selectById(HttpServletRequest request, @RequestParam(value = "id", required = false, defaultValue = "1") int id) {
        HttpSession session = request.getSession();
        logger.info("{}", session.getId());
        return taskService.selectById(id);
    }

    @RequestMapping(value = "/saveOneTask", method = RequestMethod.POST)
    public int saveOneTask(@RequestParam(value = "name", required = false, defaultValue = "1") String name) {
        return taskService.saveOneTask(name);
    }

    @RequestMapping(value = "/saveManyTask", method = RequestMethod.POST)
    public int saveManyTask(@RequestBody String body) {
        List<Task> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            task.setName(UUID.randomUUID().toString());
            list.add(task);
        }
        return taskService.saveManyTask(list);
    }

    @RequestMapping(value = "/upgById", method = RequestMethod.PUT)
    public int updateTaskById(@RequestParam(value = "id") int id,
                              @RequestParam(value = "name") String name) {
        return taskService.updateTaskById(id, name);
    }

    @RequestMapping(value = "/delById", method = RequestMethod.DELETE)
    public int deleteTaskById(@RequestParam(value = "id") int id) {
        return taskService.deleteTaskById(id);
    }
}
