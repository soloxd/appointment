package com.zzszxyy.appointment.bean.vo;


import java.util.ArrayList;
import java.util.List;

import com.zzszxyy.appointment.bean.Department;

public class DepartmentVo extends Department {

	private List<DepartmentVo> children;

	public List<DepartmentVo> getChildren() {
		return children;
	}

	public void setChildren(List<DepartmentVo> children) {
		this.children = children;
	}
	
	public void addChildren(DepartmentVo child) {
		this.children.add(child);
	}
	
	public static DepartmentVo generate(Department department) {
		DepartmentVo vo = new DepartmentVo();
		
		vo.setChildren(new ArrayList<DepartmentVo>());
		vo.setId(department.getId());
		vo.setName(department.getName());
		vo.setPid(department.getPid());
		return vo;
	}
	
	
}