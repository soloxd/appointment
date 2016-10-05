package com.zzszxyy.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.zzszxyy.appointment.bean.Doctor;
import com.zzszxyy.appointment.bean.Patient;
import com.zzszxyy.appointment.conmon.Consts;
import com.zzszxyy.appointment.conmon.ResponseData;
import com.zzszxyy.appointment.mapper.DoctorMapper;
import com.zzszxyy.appointment.mapper.PatientMapper;


@RestController
@RequestMapping("/sys")
public class SysController {
    
    @Autowired
    private DoctorMapper doctorMapper;
    

    @Autowired
    private PatientMapper patientMapper;
    
    @RequestMapping(value="/adddoctor", method=RequestMethod.POST)
    public Object adddoctor() {
        
        for (int i = 100; i < 200; i++) {
            Doctor doctor = new Doctor();
            doctor.setId("" + i);
            doctor.setJobNumber("100" + i);
            doctor.setName("阿萨德" + i);
            doctor.setOpen(1);
            doctor.setSymbol("ASD");
            
            if (i < 133) {
                doctor.setDeptId("21");
                doctor.setTitle("主任医师");
            }
            
            if (i >= 133 && i < 166) {
                doctor.setDeptId("22");
                doctor.setTitle("副主任医师");
            }
            
            if (i >= 166) {
                doctor.setDeptId("24");
                doctor.setTitle("主治医师");
            }
            
            doctorMapper.insert(doctor);
        }
        
        PageHelper.startPage(1, 10);

        return new ResponseData(Consts.SUCCESS, doctorMapper.selectAll());
    }
    
    @RequestMapping(value="/addpatient", method=RequestMethod.POST)
    public Object addpatient() {
        
        for (int i = 100; i < 167; i++) {
            Patient p = new Patient();
            p.setApptCount(2);
            p.setIdNumber("612323198511220" + i);
            p.setName("患者" + i);
            p.setPhone("13335356" + i);
            p.setSex(i % 2);
            patientMapper.insert(p);
        }
        
        PageHelper.startPage(1, 10);

        return new ResponseData(Consts.SUCCESS, patientMapper.selectAll());
    }
    
    
}
