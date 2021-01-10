package com.nz.test.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskEntity {

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

    public TaskEntity() {
    }
}
