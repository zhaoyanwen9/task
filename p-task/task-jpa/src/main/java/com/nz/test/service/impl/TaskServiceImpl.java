package com.nz.test.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nz.test.dao.task.ITaskDaoJpaRepository;
import com.nz.test.entity.TaskEntity;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private JsonParser jsonParser = new JsonParser();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ITaskDaoJpaRepository taskDaoRepository;

    public String getByRp(int page, int size, String name, int type, Date sTime, Date eTime) {
        page--;
        // page为页码,数据库从0页开始
        page = page < 0 ? 0 : page;
        Pageable pageable = PageRequest.of(page, size);
        //规格定义
        Specification<TaskEntity> specification = new Specification<TaskEntity>() {
            @Override
            public Predicate toPredicate(Root<TaskEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                // 任务类型
                if (type > 0) {
                    predicateList.add(criteriaBuilder.equal(root.get("type").as(Integer.class), type));
                }
                // 任务名称
                if (null != name && !"".equals(name)) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("%");
                    stringBuffer.append(name);
                    stringBuffer.append("%");
                    predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), stringBuffer.toString()));
                }
                // 查询开始时间在 sTime 与 eTime 之间的数据，闭区间
                // if (null != sTime) {
                //     predicateList.add(criteriaBuilder.between(root.get("sTime").as(Date.class), sTime, null != eTime ? eTime : new Date()));
                // }
                Predicate[] predicates = new Predicate[predicateList.size()];
                return query.where(predicateList.toArray(predicates)).getRestriction();
            }
        };
        Page<TaskEntity> entityPage = taskDaoRepository.findAll(specification, pageable);
        String result = new Gson().toJson(entityPage);
        return result;
    }

    public String add(TaskEntity taskEntity) {
        return new Gson().toJson(taskDaoRepository.save(taskEntity));
    }

    @Transactional(rollbackFor = IllegalArgumentException.class)
    public String save(TaskEntity taskEntity) {
        String result;
        String name = taskEntity.getName();
        List<TaskEntity> taskEntityList = taskDaoRepository.getByName(name);
        if (taskEntityList.size() > 0) {
            throw new IllegalArgumentException(name + ":" + taskEntityList.size());
        } else {
            name = taskDaoRepository.save(taskEntity).getName();
            result = name + " ok.";
        }
        return result;
    }

    public String deleteById() {
        return "delete by id";
    }

    public String editById(TaskEntity taskEntity) {
        return new Gson().toJson(taskDaoRepository.save(taskEntity));
    }

    public String getAll() {
        return new Gson().toJson(taskDaoRepository.findAll());
    }

    public String startById() {
        return "start";
    }

    public String stopById() {
        return "stop by id";
    }

    public List<TaskEntity> getByName(String name) {
        return taskDaoRepository.getByName(name);
    }

    public TaskEntity getById(int id) {
        return taskDaoRepository.getById(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public String updateNameById(String body) {
        int flag = -1;
        try {
            JsonObject jsonObject = jsonParser.parse(body).getAsJsonObject();
            int id = jsonObject.get("id").getAsInt();
            String name = jsonObject.get("name").getAsString();
            flag = taskDaoRepository.updateNameById(id, name);
            if (flag == 1) {
                throw new RuntimeException("这里抛出异常啦");
            }
        } catch (Exception e) {
        }
        return "ok" + flag;
    }
}
