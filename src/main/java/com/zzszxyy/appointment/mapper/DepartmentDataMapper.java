package com.zzszxyy.appointment.mapper;

import java.util.List;

import javax.persistence.Entity;

import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;

import com.zzszxyy.appointment.bean.DepartmentData;

@Entity
public interface DepartmentDataMapper extends Mapper<DepartmentData> {

	@Select("SELECT * FROM department_data WHERE id NOT IN (SELECT id from department)")
	List<DepartmentData> selectUnSet();
}
