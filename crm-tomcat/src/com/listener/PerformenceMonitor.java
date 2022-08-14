package com.listener;


import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * –‘ƒ‹º‡Ã˝
 */
@Aspect
@Component
public class PerformenceMonitor {

	private final static Log logger = LogFactory.getLog(PerformenceMonitor.class);
	 
    @Around("execution(* enfo.crm..*.*(..))")    
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {    
        long begin = System.nanoTime();  
        Object o = pjp.proceed();    
        long end = System.nanoTime();  
        
        try{
	        String args = Arrays.toString(pjp.getArgs());
	        
	        String tmp = String.format("[Monitor]%s:%s(%s):%s", pjp.getTarget().getClass()+"."+pjp.getSignature().getName(),(end-begin)/1000000, "∫¡√Î", args);
	        logger.info(tmp);  
        }catch (Exception e) {
		}
        
        return o;    
    }    
}
