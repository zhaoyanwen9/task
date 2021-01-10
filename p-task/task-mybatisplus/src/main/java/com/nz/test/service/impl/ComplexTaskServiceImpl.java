package com.nz.test.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nz.test.entity.Task;
import com.nz.test.mapper.TaskMapper;
import com.nz.test.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Service(value = "complexTaskServiceImpl")
public class ComplexTaskServiceImpl extends ServiceImpl<TaskMapper, Task>  implements TaskService {

    @Autowired
    @Qualifier(value = "taskMapper")
    private TaskMapper taskMapper;

    @Resource(name = "taskMapper", type = TaskMapper.class)
    private TaskMapper taskMapper2;

    @Override
    public IPage<Task> getByRp(Task task, int page, int size) {
        return null;
    }

    @Override
    public List<Task> getByName(String name) {
        return taskMapper2.getByName(name);
    }

    @Override
    public List<Task> getByNameAndStatus(String name, int status) {
        return taskMapper2.getByNameAndStatus(name, status);
    }

    @Override
    public HashMap selectById(int id) {
        return taskMapper.selectById(id);
    }

    @Override
    public int saveOneTask(String name) {
        Task task = new Task();
        return taskMapper.saveOneTask(name);
    }

    @Override
    public int saveManyTask(List<Task> list) {
        return taskMapper.saveManyTask(list);
    }

    @Override
    public int updateTaskById(int id, String name) {
        return taskMapper.updateTaskById(id, name);
    }

    @Override
    public int deleteTaskById(int id) {
        return taskMapper.deleteTaskById(id);
    }
}
