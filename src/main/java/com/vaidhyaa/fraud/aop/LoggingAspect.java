package com.vaidhyaa.fraud.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	private static final Logger log =  LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.vaidhyaa.fraud.controller..*(..))")
    public void logBefore(JoinPoint jp) {
        log.info("➡️ Entering: " + jp.getSignature());
    }

    @AfterReturning(pointcut = "execution(* com.vaidhyaa.fraud.service..*(..))", returning = "result")
    public void logAfter(JoinPoint jp, Object result) {
        log.info("✅ Completed: " + jp.getSignature());
    }
    @AfterThrowing(
            pointcut = "execution(* com.vaidhyaa.fraud.service..*(..))",
            throwing = "ex"
        )
    public void logException(JoinPoint jp, Exception ex) {
    	log.error("❌ Exception in {} : {}", jp.getSignature(), ex.getMessage());
    }
}