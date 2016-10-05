package com.zzszxyy.appointment.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zzszxyy.appointment.bean.Department;
import com.zzszxyy.appointment.bean.vo.DepartmentVo;
import com.zzszxyy.appointment.conmon.Consts;
import com.zzszxyy.appointment.conmon.ResponseData;
import com.zzszxyy.appointment.mapper.DepartmentDataMapper;
import com.zzszxyy.appointment.mapper.DepartmentMapper;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	private @Resource DepartmentMapper departmentMapper;
	private @Resource DepartmentDataMapper departmentDataMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	public Object list() {
		List<Department> all = departmentMapper.selectAll();
		Map<String, DepartmentVo> models = new LinkedHashMap<String, DepartmentVo>();
		for (Department department : all) {
			models.put(department.getId(), DepartmentVo.generate(department));
		}
		
		List<DepartmentVo> top = new ArrayList<>();
		
		for (Entry<String, DepartmentVo> entry : models.entrySet()) {
			if (StringUtils.equals(entry.getValue().getPid(), "0")) {
				top.add(entry.getValue());
			} else if (models.get(entry.getValue().getPid()) != null){
				models.get(entry.getValue().getPid()).addChildren(entry.getValue());
			}
		}
		
		return new ResponseData(Consts.SUCCESS, top);
	}
	
	@RequestMapping(value="/data", method = RequestMethod.GET)
	public Object data() {
		return new ResponseData(Consts.SUCCESS, departmentDataMapper.selectUnSet());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Transactional
	public Object edit(@RequestBody DepartmentVo vo) {
		departmentMapper.deleteByPrimaryKey(vo.getId());
		Department condition = new Department();
		condition.setPid(vo.getId());
		for (Department child : departmentMapper.select(condition)) {
			departmentMapper.deleteByPrimaryKey(child.getId());
		}
		vo.setPid("0");
		departmentMapper.insertSelective(vo);
		for (DepartmentVo child : vo.getChildren()) {
			departmentMapper.insertSelective(child);
		}
		
		return new ResponseData(Consts.SUCCESS);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@Transactional
	public Object remove(@PathVariable("id") String id) {
		departmentMapper.deleteByPrimaryKey(id);
		Department condition = new Department();
		condition.setPid(id);
		departmentMapper.delete(condition);
		return new ResponseData(Consts.SUCCESS);
	}
	
	
}
