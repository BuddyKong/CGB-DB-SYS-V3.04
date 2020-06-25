package com.cy.pj.common.aspect;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cy.pj.common.cache.DefaultMapCache;

@Aspect
@Component
public class SysCacheAspect {
	@Autowired
	public DefaultMapCache mapCache;
	@Pointcut("@annotation(com.cy.pj.common.annotation.RequiredCache)")
	public void doCache() {
	}
	@Pointcut("@annotation(com.cy.pj.common.annotation.ClearCache)")
	public void doClear() {
	}
	@AfterReturning("doClear()")
	public void doAfterReturning() {
		mapCache.clear(); 
	}
	@Around("doCache()")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("Get data from cache");
		Object obj = jp.proceed();
		System.out.println("Put data to cache");
		return obj;
	}


}