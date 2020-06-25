package com.cy.pj.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class SysExceptionAspect {
	 @AfterThrowing(value="bean(sysUserServiceImpl)",throwing = "e")
	  public void doExceptionLog(JoinPoint jp,Throwable e) {
		  MethodSignature ms=(MethodSignature)jp.getSignature();//连接点对象封装了正在执行的目标方法信息
		  log.error("{} error msg {}",ms.getName(),e.getMessage());
	  }
}
