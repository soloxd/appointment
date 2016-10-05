package com.zzszxyy.appointment.bean;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * 患者
 */
@Table(name="patient")
@NameStyle(Style.normal)
public class Patient {

    /**
     * 身份证号码
     */
    @Id
    private String idNumber;
    
    private String name;

    /**
     * 性别（0:女  1:男）
     */
    private Integer sex;

    private String phone;

    /**
     * 预约次数
     */
    private Integer apptCount;

    /**
     * 违约次数
     */
    private Integer defaultCount;
    
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date regTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getApptCount() {
        return apptCount;
    }

    public void setApptCount(Integer apptCount) {
        this.apptCount = apptCount;
    }

    public Integer getDefaultCount() {
        return defaultCount;
    }

    public void setDefaultCount(Integer defaultCount) {
        this.defaultCount = defaultCount;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }
    
}
