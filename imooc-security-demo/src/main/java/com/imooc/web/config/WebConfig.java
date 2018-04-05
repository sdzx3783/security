package com.imooc.web.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.imooc.web.controller.TimeInterceptor;
import com.imooc.web.filter.TimeFilter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
	@Autowired
	private TimeInterceptor timeInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(timeInterceptor);
	}
	@Bean
	public FilterRegistrationBean timeFilter(){
		FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
		TimeFilter timeFilter=new TimeFilter();
		filterRegistrationBean.setFilter(timeFilter);
		List<String> urls=new ArrayList<>();
		urls.add("/*");
		filterRegistrationBean.setUrlPatterns(urls);
		return filterRegistrationBean;
	}
}
