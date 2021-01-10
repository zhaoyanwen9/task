package com.nz.test.dao.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 1、idea安装lombok插件
 * 2、添加maven依赖 <dependency><groupId>org.projectlombok</groupId><artifactId>lombok</artifactId><version>1.16.10</version></dependency>
 * 3、添加注解：@Data、@Getter/@Setter
 */
@Data
@Getter
@Setter
public class Task {

    private Integer id;

    private String name;

    private Integer type;

    private Integer status;

    private Long cTime;

    private Long sTime;

    private Long eTime;

    private Integer progress;

    private Integer startStyle;

    private String startParam;

    private Integer serviceId;

    private Integer envType;

    private String envId;

    private String faultId;

    private Integer caseId;

    private String failReason;

    private String executeLog;

    private boolean isDelete;

    public Task() {
    }
}
