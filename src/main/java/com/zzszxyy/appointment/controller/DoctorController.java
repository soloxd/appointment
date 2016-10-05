package com.zzszxyy.appointment.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zzszxyy.appointment.bean.Doctor;
import com.zzszxyy.appointment.conmon.Consts;
import com.zzszxyy.appointment.conmon.ResponseData;
import com.zzszxyy.appointment.mapper.DoctorMapper;


@RestController
@RequestMapping("/doctor")
public class DoctorController {
    
    @Autowired
    private DoctorMapper doctorMapper;
    
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public Object list(Doctor doctor, Integer page, Integer size) {
        int count = doctorMapper.count(doctor);
        int total = size == null ? 1 : count / size + (count % size == 0 ? 0 : 1);

        Map<String, Object> map = new HashMap<>(3);
        map.put("count", count);
        map.put("total", total);
        map.put("list", doctorMapper.list(doctor, page, size));
        
        return new ResponseData(Consts.SUCCESS, map);
    }
    
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public Object save(Doctor doctor) {
        doctorMapper.updateByPrimaryKeySelective(doctor);
        return new ResponseData(Consts.SUCCESS);
    }
    
}
