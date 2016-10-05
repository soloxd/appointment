package com.zzszxyy.appointment.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zzszxyy.appointment.bean.Patient;
import com.zzszxyy.appointment.conmon.Consts;
import com.zzszxyy.appointment.conmon.ResponseData;
import com.zzszxyy.appointment.mapper.PatientMapper;


@RestController
@RequestMapping("/patient")
public class PatientController {
    
    @Autowired
    private PatientMapper patientMapper;
    
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public Object list(Patient patient, Integer page, Integer size) {
        int count = patientMapper.count(patient);
        int total = size == null ? 1 : count / size + (count % size == 0 ? 0 : 1);

        Map<String, Object> map = new HashMap<>(3);
        map.put("count", count);
        map.put("total", total);
        map.put("list", patientMapper.list(patient, page, size));
        
        return new ResponseData(Consts.SUCCESS, map);
    }
    
    @RequestMapping(value="/blackList", method=RequestMethod.POST)
    public Object blackList(Patient patient) {
        patient.setDefaultCount(4);  // 违约4次即加入黑名单
        return new ResponseData(Consts.SUCCESS, patientMapper.list(patient, null, null));
    }
    
    @RequestMapping(value="/unlock", method=RequestMethod.POST)
    public Object unlock(String idNumber) {
        Patient patient = patientMapper.selectByPrimaryKey(idNumber);
        
        if (patient == null) {
            return new ResponseData(Consts.FAILED, "患者 " + idNumber + " 不存在");
        } else {
            patient = new Patient();
            patient.setIdNumber(idNumber);
            patient.setDefaultCount(0);
            patientMapper.updateByPrimaryKeySelective(patient);
        }
        
        return new ResponseData(Consts.SUCCESS);
    }
    
}
