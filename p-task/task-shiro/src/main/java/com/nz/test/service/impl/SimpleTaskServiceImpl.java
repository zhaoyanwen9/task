package com.nz.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nz.test.dao.domain.Task;
import com.nz.test.dao.mapper.TaskMapper;
import com.nz.test.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Spring创建一个名字叫simpleTaskServiceImpl的SimpleTaskServiceImpl实例
 * Controller需要使用Spring创建的名字为simpleTaskServiceImpl的SimpleTaskServiceImpl实例时,使用@Resource(name = "simpleTaskServiceImpl")注解告诉Spring,Spring把创建好的实例注入给Controller即可。
 */
@Service(value = "simpleTaskServiceImpl")
public class SimpleTaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    // @Autowired 根据类型注入，
    // @Resource 默认根据名字注入，其次按照类型搜索
    // @Autowired + @Qualifie("beanName") 两个结合起来可以根据名字和类型注入

    /**
     * Spring规范
     * byBeanType装配:
     * required: 是否为空,默认true
     * + @Qualifier: BeanName装配
     * value: bean名称
     */
    @Autowired
    @Qualifier(value = "taskMapper")
    private TaskMapper taskMapper;

    /**
     * JSR-250规范定义属J2EE的,减少了与spring的耦合
     * byBeanName装配
     * name属性: bean名称
     * type属性: bean类型
     */
    @Resource(name = "taskMapper", type = TaskMapper.class)
    private TaskMapper taskMapper2;

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
