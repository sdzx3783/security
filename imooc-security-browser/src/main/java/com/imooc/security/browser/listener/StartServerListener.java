package com.imooc.security.browser.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.ContextLoaderListener;

import com.imooc.security.browser.util.AppUtil;
@WebListener
public class StartServerListener extends ContextLoaderListener {
	@Override
	public void contextInitialized(ServletContextEvent event) {
		AppUtil.init(event.getServletContext());
	}

}
