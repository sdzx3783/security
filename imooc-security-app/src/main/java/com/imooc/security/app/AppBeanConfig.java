package com.imooc.security.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
@Order(Integer.MIN_VALUE)
@Configuration
public class AppBeanConfig {
	@Bean 
	public DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler(){
		return new DefaultWebSecurityExpressionHandler();
	}
}
