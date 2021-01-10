package com.nz.test.service;

import com.google.gson.Gson;
import com.nz.test.entity.AliasTaskEntity;
import com.nz.test.entity.TaskEntity;
import com.nz.test.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "taskService")
public class TaskService {

    private Gson gson = new Gson();

    @Autowired
    private TaskMapper taskMapper;

    public String getByRp(int page, int size) {
        List<TaskEntity> taskList = taskMapper.getByRp(page, size);
        return gson.toJson(taskList);
    }

    public String getByName(String name) {
        List<AliasTaskEntity> taskList = taskMapper.getByName(name);
        return gson.toJson(taskList);
    }

    public int getCount() {
        return taskMapper.getCount();
    }

    public Map<String, Object> getCruxInfo(int id) {
        return taskMapper.getCruxInfo(id);
    }

    // @Transactional(rollbackFor = Throwable.class)
    public String getById(int id) {
        TaskEntity task = taskMapper.getById(id);
        return gson.toJson(task);
    }

    public List<Map<String, Object>> getAll() {
        return taskMapper.getAll();
    }

    public List<Map<String, Object>> getMany(int id) {
        return taskMapper.getMany(id);
    }
}
