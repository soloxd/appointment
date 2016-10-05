package com.zzszxyy.appointment.bean;

import javax.persistence.Table;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * 科室
 */
@Table(name="department")
@NameStyle(Style.normal)
public class Department extends DepartmentData {

	private String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }


}
