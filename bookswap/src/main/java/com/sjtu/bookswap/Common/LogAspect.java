package com.sjtu.bookswap.Common;


import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.sjtu.donate.Controller..*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        if(AppSettings.LOG_FLAG == 2) {
            logger.info("URL : " + request.getRequestURL().toString());
            logger.info("HTTP_METHOD : " + request.getMethod());
            logger.info("IP : " + request.getRemoteAddr());
            logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        }
        else if(AppSettings.LOG_FLAG == 1) {
        	System.out.println("[Request: "+request.getRequestURL().toString()+"]: "+Arrays.toString(joinPoint.getArgs()));
        }

    }

    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(Object ret) throws Throwable {
    	if(AppSettings.LOG_FLAG == 2) {
    		 logger.info("RESPONSE : " + ret);
    	     logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    	}
    	else if(AppSettings.LOG_FLAG == 1) {
    		System.out.println("[Response]: "+ret);
    	}
    	
       
    }
}