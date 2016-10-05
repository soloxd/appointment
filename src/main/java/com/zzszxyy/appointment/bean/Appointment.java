package com.zzszxyy.appointment.bean;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 预约信息
 */
@Table(name="appointment")
@NameStyle(Style.normal)
public class Appointment {
    
    @Id
    private Integer id;
    
    private String patientId;
    private String patientName;
    private String patientPhone;
    private String patientSex;
    

    private String doctorId;
    private String doctorName;
    private String doctorTitle;
    private String doctorSymbol;
    
    private String deptId;
    private String deptName;
    
    private String apptCode;
    private String apptDate;
    private String apptType;
    
    @Transient
    private String apptDateStart;
    
    @Transient
    private String apptDateEnd;
    
    private String regPerson;
    
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date regTime;
    
    /**
     * 挂号方式（0:电话预约  1:网上预约  2:现场预约）
     */
    private String regWay;
    
    /**
     * 状态（0:未登记  1:已登记  2:已取消）
     */
    private String status;
    
    /**
     * 短信通知（0:已发送  2:未发送）
     */
    private Integer sms;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getApptCode() {
        return apptCode;
    }

    public void setApptCode(String apptCode) {
        this.apptCode = apptCode;
    }

    public String getApptDate() {
        return apptDate;
    }

    public void setApptDate(String apptDate) {
        this.apptDate = apptDate;
    }

    public String getApptType() {
        return apptType;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public String getRegPerson() {
        return regPerson;
    }

    public void setRegPerson(String regPerson) {
        this.regPerson = regPerson;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getRegWay() {
        return regWay;
    }

    public void setRegWay(String regWay) {
        this.regWay = regWay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSms() {
        return sms;
    }

    public void setSms(Integer sms) {
        this.sms = sms;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorTitle() {
        return doctorTitle;
    }

    public void setDoctorTitle(String doctorTitle) {
        this.doctorTitle = doctorTitle;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDoctorSymbol() {
        return doctorSymbol;
    }

    public void setDoctorSymbol(String doctorSymbol) {
        this.doctorSymbol = doctorSymbol;
    }

    public String getApptDateStart() {
        return apptDateStart;
    }

    public void setApptDateStart(String apptDateStart) {
        this.apptDateStart = apptDateStart;
    }

    public String getApptDateEnd() {
        return apptDateEnd;
    }

    public void setApptDateEnd(String apptDateEnd) {
        this.apptDateEnd = apptDateEnd;
    }
 
}
