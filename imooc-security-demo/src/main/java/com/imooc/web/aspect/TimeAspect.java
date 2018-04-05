package com.imooc.web.aspect;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeAspect {
	@Around("execution(* com.imooc.web.controller.UserController.*(..))")
	public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable{
		System.out.println("time aspect start");
		long start=new Date().getTime();
		Object[] args = pjp.getArgs();
		for (Object object : args) {
			System.out.println("参数："+object);
		}
		Object proceed = pjp.proceed();
		System.out.println("aop 耗时： "+(new Date().getTime()-start));
		System.out.println("time aspect end");
		return proceed;
	}
}
