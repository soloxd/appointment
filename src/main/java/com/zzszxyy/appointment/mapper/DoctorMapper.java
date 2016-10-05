package com.zzszxyy.appointment.mapper;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import tk.mybatis.mapper.common.Mapper;

import com.zzszxyy.appointment.bean.Doctor;

@Entity
public interface DoctorMapper extends Mapper<Doctor> {

    @SelectProvider(type=Provider.class, method="list")
    List<Doctor> list(@Param("doctor") Doctor doctor, @Param("page") Integer page, @Param("size") Integer size);
    
    @SelectProvider(type=Provider.class, method="count")
    int count(@Param("doctor") Doctor doctor);
    
    class Provider {
    
        public String list(Map<String, Object> param) {
            Doctor doctor = (Doctor) param.get("doctor");
            Integer page = (Integer) param.get("page");
            Integer size = (Integer) param.get("size");
            
            StringBuffer sql = new StringBuffer();
            sql.append("select d.*, dept.name deptName")
               .append(" from doctor d left join department dept on d.deptId = dept.id")
               .append(whereStr(doctor))
               .append(" order by jobNumber");
            
            if (page != null && size != null) {
                int start = ((page - 1) * size);
                sql.append(" limit " + start + ", #{size}");
            }
            
            return sql.toString();
        }
        
        public String count(Map<String, Object> param) {
            Doctor doctor = (Doctor) param.get("doctor");
            
            StringBuffer sql = new StringBuffer();
            sql.append("select count(d.id)")
               .append(" from doctor d left join department dept on d.deptId = dept.id")
               .append(whereStr(doctor));
            
            return sql.toString();
        }
        
        private String whereStr(Doctor doctor) {
            if (doctor == null) return "";
            
            StringBuffer sql = new StringBuffer(" where 1 = 1");
            
            if (StringUtils.isNotBlank(doctor.getDeptId())) {
                sql.append(" and (dept.id = #{doctor.deptId} or dept.pid = #{doctor.deptId})");
            }
            
            if (StringUtils.isNotBlank(doctor.getTitle())) {
                sql.append(" and d.title = #{doctor.title}");
            }
            
            if (doctor.getOpen() != null) {
                sql.append(" and d.open = #{doctor.open}");
            }
            
            if (StringUtils.isNotBlank(doctor.getName())) {
                sql.append(" and (d.name like concat('%', #{doctor.name}, '%') or d.symbol = #{doctor.name})");
            }
            
            return sql.toString();
        }
        
    }
}
