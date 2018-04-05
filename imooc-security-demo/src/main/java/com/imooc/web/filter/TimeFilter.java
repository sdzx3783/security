package com.imooc.web.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//@Component (不用注解 模拟第三方过滤器加入到本项目)
public class TimeFilter implements Filter {

	@Override
	public void destroy() {
		System.out.println("time filter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("time filter start");
		long start=new Date().getTime();
		chain.doFilter(request, response);
		System.out.println("耗时： "+(new Date().getTime()-start));
		System.out.println("time filter finish");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("time filter init");
	}

}
