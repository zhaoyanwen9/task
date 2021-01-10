package com.nz.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nz.test.dao.domain.Task;

import java.util.HashMap;
import java.util.List;

public interface TaskService extends IService<Task> {

    /**
     * 名称查询
     * @param name
     * @return
     */
    List<Task> getByName(String name);

    /**
     * 名称 + 状态查询
     * @param name
     * @param status
     * @return
     */
    List<Task> getByNameAndStatus(String name, int status);

    /**
     * 主键查询
     * @param id
     * @return
     */
    HashMap selectById(int id);

    /**
     * 单对象保存
     * @param name
     * @return
     */
    int saveOneTask(String name);

    /**
     * 多对象保存
     * @param list
     * @return
     */
    int saveManyTask(List<Task> list);

    /**
     * 根据主键更新
     * @param id
     * @param name
     * @return
     */
    int updateTaskById(int id, String name);

    /**
     * 根据主键删除
     * @param id
     * @return
     */
    int deleteTaskById(int id);
}
