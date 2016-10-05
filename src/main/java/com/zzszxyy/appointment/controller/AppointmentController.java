package com.zzszxyy.appointment.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zzszxyy.appointment.bean.Appointment;
import com.zzszxyy.appointment.conmon.Consts;
import com.zzszxyy.appointment.conmon.ResponseData;
import com.zzszxyy.appointment.mapper.AppointmentMapper;


@RestController
@RequestMapping("/appt")
public class AppointmentController {
    
    @Autowired
    private AppointmentMapper apptMapper;
    
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public Object list(Appointment appt, Integer page, Integer size) {
        int count = apptMapper.count(appt);
        int total = size == null ? 1 : count / size + (count % size == 0 ? 0 : 1);

        Map<String, Object> map = new HashMap<>(3);
        map.put("count", count);
        map.put("total", total);
        map.put("list", apptMapper.list(appt, page, size));
        
        return new ResponseData(Consts.SUCCESS, map);
    }
    
}
