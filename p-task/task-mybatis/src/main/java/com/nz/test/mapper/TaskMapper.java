package com.nz.test.mapper;

import com.nz.test.entity.AliasTaskEntity;
import com.nz.test.entity.TaskEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TaskMapper {

    /**
     * 根据名称获取
     * @return
     */
    List<AliasTaskEntity> getByName(@Param("name") String name);

    /**
     * 获取数量
     *
     * @return
     */
    int getCount();

    /**
     * 获取关键信息
     *
     * @param id
     * @return
     */
    Map<String, Object> getCruxInfo(int id);

    /**
     * 根据主键获取
     *
     * @param id
     * @return
     */
    TaskEntity getById(int id);

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    List<TaskEntity> getByRp(int page, int size);

    /**
     * 获取所有
     *
     * @return
     */
    List<Map<String, Object>> getAll();

    List<Map<String, Object>> getMany(int id);
}
