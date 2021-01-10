package com.nz.test.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nz.test.dao.domain.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * 1、@Mapper不需配置扫描地址,通过xml里面的namespace里面的接口生成Bean后注入。
 * 2、@Mapper一般用在接口上,针对的是一个一个的类,太麻烦, @MapperScan 应用而生,@MapperScan 配置一个或多个包路径,自动的扫描这些包路径下的类,自动的为它们生成代理类
 * 3、Mybatis有一个拦截器自动的把 @Mapper 注解的接口生成动态代理类。可在 MapperRegistry 类中的源代码中查看。
 * 4、@Repository需在Spring中配置扫描地址后生成的Bean才能被注入
 * 5、@MapperScan 注解，将会生成 MapperFactoryBean，可在 AutoConfiguredMapperScannerRegistrar 和 MybatisAutoConfiguration 类中查看源代码进行分析
 * 6、动态代理和 AOP
 * @author nz
 */
@Repository("taskMapper")
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * 根据任务名称查询任务
     *
     * @param name
     * @return
     */
    @Select("select * from tb_task where name = #{name}")
    List<Task> getByName(String name);

    /**
     * 根据任务名称和状态查询
     * @param name
     * @param status
     * @return
     */
    @Select("select * from tb_task where name = #{name} and status = #{status}")
    List<Task> getByNameAndStatus(String name, int status);

    /**
     * 根据主键获取对象所有字段
     *
     * @param id
     * @return
     */
    Task getById(int id);

    /**
     * 根据主键获取对象部分字段
     *
     * @param id
     * @return
     */
    HashMap selectById(int id);

    /**
     * 保存单个对象
     *
     * @param name
     * @return
     */
    int saveOneTask(String name);

    /**
     * 保存多个对象
     *
     * @param list
     * @return
     */
    int saveManyTask(List<Task> list);

    /**
     * 根据主键更新对象属性
     *
     * @param id
     * @param name
     * @return
     */
    int updateTaskById(int id, String name);

    /**
     * 根据主键删除对象
     *
     * @param id
     * @return
     */
    int deleteTaskById(int id);
}
