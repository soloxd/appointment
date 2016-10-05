package com.zzszxyy.appointment.conmon;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
@Aspect
public class ControllerAspectj {

	private static final Logger LOG = LoggerFactory.getLogger(ControllerAspectj.class);
	
	@Around("execution(* com.zzszxyy.appointment.controller..*.*(..))")
	public Object myAroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
	    String classMethod = pjp.getSignature().toShortString();
	    LOG.debug("进入 {}", classMethod);
	    long stime = System.currentTimeMillis();
	    Object obj = null;
	    
	    Object[] objs = pjp.getArgs();
	    for (int i = 0; i < objs.length; i++) {
	        LOG.debug("请求参数{}: {}", i+1, JSON.toJSONString(objs[i]));
        }
	    
		try {
			obj = pjp.proceed(pjp.getArgs());
		} catch(ServiceException e) {
		    LOG.error(e.getLocalizedMessage(), e);
            obj = new ResponseData(Consts.FAILED, e.getMessage());
		} catch(Exception e){
			LOG.error(e.getLocalizedMessage(), e);
            obj = new ResponseData(Consts.FAILED, 
                    e.getClass().getName() + ": " + e.getLocalizedMessage());
		}

        long etime = System.currentTimeMillis();
        LOG.debug("退出 {} 请求执行耗时: {}ms", classMethod, etime-stime);
		return obj;
	}
	
}
