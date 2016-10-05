package com.zzszxyy.appointment.mapper;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import tk.mybatis.mapper.common.Mapper;

import com.zzszxyy.appointment.bean.Patient;

@Entity
public interface PatientMapper extends Mapper<Patient> {

    @SelectProvider(type=Provider.class, method="list")
    List<Patient> list(@Param("patient") Patient patient, @Param("page") Integer page, @Param("size") Integer size);
    
    @SelectProvider(type=Provider.class, method="count")
    int count(@Param("patient") Patient patient);
    
    class Provider {
    
        public String list(Map<String, Object> param) {
            Patient patient = (Patient) param.get("patient");
            Integer page = (Integer) param.get("page");
            Integer size = (Integer) param.get("size");
            
            StringBuffer sql = new StringBuffer();
            sql.append("select * from patient")
               .append(whereStr(patient))
               .append(" order by regTime desc");
            
            if (page != null && size != null) {
                int start = ((page - 1) * size);
                sql.append(" limit "+ start +", #{size}");
            }
            
            return sql.toString();
        }
        
        public String count(Map<String, Object> param) {
            Patient patient = (Patient) param.get("patient");
            
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) from patient")
               .append(whereStr(patient));
            
            return sql.toString();
        }
        
        private String whereStr(Patient patient) {
            if (patient == null) return "";
            
            StringBuffer sql = new StringBuffer(" where 1 = 1");
            
            if (StringUtils.isNotBlank(patient.getName())) {
                sql.append(" and name = #{patient.name}");
            }
            
            if (StringUtils.isNotBlank(patient.getPhone())) {
                sql.append(" and phone = #{patient.phone}");
            }
            
            if (StringUtils.isNotBlank(patient.getIdNumber())) {
                sql.append(" and idNumber = #{patient.idNumber}");
            }
            
            if (patient.getSex() != null) {
                sql.append(" and sex = #{patient.sex}");
            }
            
            if (patient.getDefaultCount() != null) {
                sql.append(" and defaultCount >= #{patient.defaultCount}");
            }
            
            return sql.toString();
        }
        
    }
    
}
