package com.zzszxyy.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zzszxyy.appointment.bean.Department;
import com.zzszxyy.appointment.conmon.Consts;
import com.zzszxyy.appointment.conmon.ResponseData;
import com.zzszxyy.appointment.mapper.DepartmentMapper;


@RestController
@RequestMapping("/select")
public class SelectController {
    
    @Autowired
    private DepartmentMapper deptMapper;
    
    @RequestMapping(value="/deptListByPid", method=RequestMethod.POST)
    public Object deptListByPid(String pid) {
        Department dept = new Department();
        dept.setPid(pid);
        return new ResponseData(Consts.SUCCESS, deptMapper.select(dept));
    }
    
}
