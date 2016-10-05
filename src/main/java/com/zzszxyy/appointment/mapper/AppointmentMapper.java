package com.zzszxyy.appointment.mapper;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import tk.mybatis.mapper.common.Mapper;

import com.zzszxyy.appointment.bean.Appointment;

@Entity
public interface AppointmentMapper extends Mapper<Appointment> {

    @SelectProvider(type=Provider.class, method="list")
    List<Appointment> list(@Param("appt") Appointment appt, @Param("page") Integer page, @Param("size") Integer size);
    
    @SelectProvider(type=Provider.class, method="count")
    int count(@Param("appt") Appointment appt);
    
    class Provider {
    
        public String list(Map<String, Object> param) {
            Appointment appt = (Appointment) param.get("appt");
            Integer page = (Integer) param.get("page");
            Integer size = (Integer) param.get("size");
            
            StringBuffer sql = new StringBuffer();
            sql.append("select * from appointment")
               .append(whereStr(appt))
               .append(" order by regTime desc");
            
            if (page != null && size != null) {
                int start = ((page - 1) * size);
                sql.append(" limit " + start + ", #{size}");
            }
            
            return sql.toString();
        }
        
        public String count(Map<String, Object> param) {
            Appointment appt = (Appointment) param.get("appt");
            
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) from appointment")
               .append(whereStr(appt));
            
            return sql.toString();
        }
        
        private String whereStr(Appointment appt) {
            if (appt == null) return "";
            
            StringBuffer sql = new StringBuffer(" where 1 = 1");
            
            if (StringUtils.isNotBlank(appt.getDeptId())) {
                sql.append(" and deptId = #{appt.deptId}");
            }
            
            if (StringUtils.isNotBlank(appt.getDoctorName())) {
                sql.append(" and (doctorName = #{appt.doctorName} or doctorSymbol = #{appt.doctorName})");
            }
            
            if (StringUtils.isNotBlank(appt.getRegWay())) {
                sql.append(" and regWay = #{appt.regWay}");
            }
            
            if (StringUtils.isNotBlank(appt.getStatus())) {
                sql.append(" and status = #{appt.status}");
            }
            
            if (StringUtils.isNotBlank(appt.getRegPerson())) {
                sql.append(" and regPerson = #{appt.regPerson}");
            }
            
            if (StringUtils.isNotBlank(appt.getPatientName())) {
                sql.append(" and (patientName = #{appt.patientName} or patientPhone = #{appt.patientPhone})");
            }
            
            if (StringUtils.isNotBlank(appt.getApptDateStart())) {
                sql.append(" and apptDate >= #{appt.apptDateStart}");
            }
            
            if (StringUtils.isNotBlank(appt.getApptDateEnd())) {
                sql.append(" and apptDate <= #{appt.apptDateEnd}");
            }
            
            return sql.toString();
        }
        
    }
    
}
