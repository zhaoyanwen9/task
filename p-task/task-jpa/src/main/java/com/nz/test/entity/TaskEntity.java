package com.nz.test.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Entity 实体类的标识
 * @Table 实体类与其映射的数据库表名不同名时需要使用@Table标注说明
 * 1. name,指明数据库的表名
 * 2. catalog和schema设置表所属的数据库目录或模式
 */
@Entity
@Table(name = "tb_task")
public class TaskEntity {

    /**
     * @Id 标识实体类的属性映射对应表中的主键
     * @GeneratedValue 主键生成策略
     * 1. strategy指定具体的生成策略
     * 方式一: @GeneratedValue(strategy=GenerationType.AUTO) 也是默认策略， 即写成@GeneratedValue也可；
     * 方式二: @GeneratedValue(strategy = GenerationType.IDENTITY)指定“自动增长”策略，适用于MySQL；
     * 方式三: @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = “seq_tbl_person”)指定“序列”策略，常用于Oracle
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * name:数据库表字段名,默认一致。
     * nullable:是否为null,默认为true。
     * unique:是否唯一,默认为false。
     * length: 字段的大小,仅对String类型的字段有效。
     * insertable：表示在ORM框架执行插入操作时，该字段是否应出现INSETRT语句中，默认为true。
     * updateable：表示在ORM框架执行更新操作时，该字段是否应该出现在UPDATE语句中，默认为true。对于一经创建就不可以更改的字段，该属性非常有用，如对于birthday字段。
     * columnDefinition：表示该字段在数据库中的实际类型。通常ORM框架可以根据属性类型自动判断数据库中字段的类型，但是对于Date类型仍无法确定数据库中字段类型究竟是DATE，TIME还是TIMESTAMP。此外，String的默认映射类型为VARCHAR，如果要将String类型映射到特定数据库的BLOB或TEXT字段类型，该属性非常有用。
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "type")
    private Integer type;

    @Column(name = "status")
    private Integer status;

    @Column(name = "c_time")
    private Long cTime;

    @Column(name = "s_time")
    private Long sTime;

    @Column(name = "e_time")
    private Long eTime;

    @Column(name = "progress")
    private Integer progress;

    @Column(name = "start_style")
    private Integer startStyle;

    @Column(name = "start_param")
    private String startParam;

    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "env_type")
    private Integer envType;

    @Column(name = "env_id")
    private String envId;

    @Column(name = "fault_id")
    private String faultId;

    @Column(name = "case_id")
    private Integer caseId;

    @Column(name = "fail_reason")
    private String failReason;

    @Column(name = "execute_log")
    private String executeLog;

    @Column(name = "is_delete")
    private boolean isDelete;

    public TaskEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getcTime() {
        return cTime;
    }

    public void setcTime(Long cTime) {
        this.cTime = cTime;
    }

    public Long getsTime() {
        return sTime;
    }

    public void setsTime(Long sTime) {
        this.sTime = sTime;
    }

    public Long geteTime() {
        return eTime;
    }

    public void seteTime(Long eTime) {
        this.eTime = eTime;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getStartStyle() {
        return startStyle;
    }

    public void setStartStyle(Integer startStyle) {
        this.startStyle = startStyle;
    }

    public String getStartParam() {
        return startParam;
    }

    public void setStartParam(String startParam) {
        this.startParam = startParam;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getEnvType() {
        return envType;
    }

    public void setEnvType(Integer envType) {
        this.envType = envType;
    }

    public String getEnvId() {
        return envId;
    }

    public void setEnvId(String envId) {
        this.envId = envId;
    }

    public String getFaultId() {
        return faultId;
    }

    public void setFaultId(String faultId) {
        this.faultId = faultId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getExecuteLog() {
        return executeLog;
    }

    public void setExecuteLog(String executeLog) {
        this.executeLog = executeLog;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
