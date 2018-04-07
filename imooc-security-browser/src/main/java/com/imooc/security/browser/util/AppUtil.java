package com.imooc.security.browser.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class AppUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	private static ServletContext servletContext;

	public static void init(ServletContext _servletContext) {
		servletContext = _servletContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public static <C> C getBean(Class<C> cls) {
		return (C) applicationContext.getBean(cls);
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static ApplicationContext getContext() {
		return applicationContext;
	}

	public static List<Class> getImplClass(Class clazz) throws ClassNotFoundException {
		List<Class> list = new ArrayList();

		Map map = applicationContext.getBeansOfType(clazz);
		for (Object obj : map.values()) {
			String name = obj.getClass().getName();
			int pos = name.indexOf("$$");
			if (pos > 0) {
				name = name.substring(0, name.indexOf("$$"));
			}
			Class cls = Class.forName(name);
			list.add(cls);
		}
		return list;
	}
}
