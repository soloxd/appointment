package com.zzszxyy.appointment.bean;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * 医生
 */
@Table(name="doctor")
@NameStyle(Style.normal)
public class Doctor {

    @Id
    private String id;
    private String name;
    
    /**
     * 职称
     */
    private String title;
    
    private String deptId;

    @Transient
    private String deptName;
    
    /**
     * 助记符
     */
    private String symbol;
    
    private String jobNumber;
    
    /**
     * 是否可预约（0:否  1:是）
     */
    private Integer open;
    
    private String summary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    
}
