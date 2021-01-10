package com.nz.test.dao.task;

import com.nz.test.dao.IJpaSpecificationDao;
import com.nz.test.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 1.JPA是一套规范,内部是有接口和抽象类组成的。
 * 2.Spring Data JPA是Spring提供的一套对JPA操作的封装，是在JPA规范下的专门用来进行数据持久化的解决方案。
 *     Spring Data Jpa中只需要声明接口(面向接口编程)，框架会自动通过JDK动态代理生成接口实现类，后注入spring容器中
 *     定义的规则:
 *         查询方法以 findBy开头
 * 3.Hibernate是一套成熟的ORM框架,Hibernate实现了JPA规范,Hibernate为JPA的一种实现方式(JDBC),
 *
 * @author nz
 */
public interface ITaskDaoJpaRepository extends ITaskDao<TaskEntity>, JpaRepository<TaskEntity, Integer>, IJpaSpecificationDao<TaskEntity> {

    /**
     * 查询:根据任务名称获取最新任务配置信息
     * 1.@Query:配置sql查询,JPQL
     *     value:sql语句
     *     nativeQuery:查询方式
     *         true:sql
     *         false:jpql(默认)
     * 2.占位符: ? + 索引
     *
     * @param name
     * @return
     */
    @Query(value = "from TaskEntity where name like %?1%")
    List<TaskEntity> getByName(String name);

    /**
     * 根据ID获取任务信息
     * @param id
     * @return
     */
    @Query(value = "select * from tb_task where id = :id", nativeQuery = true)
    TaskEntity getById(@Param("id") int id);

    /**
     * 更新根据任务id更新任务名称
     * 1.@Query:SQL
     * 2.@Modifying:
     *
     * @param id
     * @param name
     */
    @Query(value = "update tb_task set name = ?2 where id = ?1", nativeQuery = true)
    @Modifying
    int updateNameById(long id, String name);
}
