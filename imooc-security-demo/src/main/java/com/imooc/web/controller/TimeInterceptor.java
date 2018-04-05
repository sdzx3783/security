package com.imooc.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TimeInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex)
			throws Exception {
		System.out.println("afterCompletion");
		long start=(long) request.getAttribute("startTime");
		System.out.println("耗时： "+(new Date().getTime()-start));
		System.out.println("ex is"+ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("postHandle");
		long start=(long) request.getAttribute("startTime");
		System.out.println("time interceptor耗时： "+(new Date().getTime()-start));
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("prehandler");
		request.setAttribute("startTime", new Date().getTime());
		HandlerMethod hm=(HandlerMethod) handler;
		System.out.println(hm.getBean().getClass().getName());
		System.out.println(hm.getMethod().getName());
		return true;
	}

}
