package com.imooc.security.browser.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.security.core.properties.LoginType;
import com.imooc.security.core.properties.SecurityProperties;
@Component("imoocAuthenticationFailureHandler")
public class ImoocAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Value("${imooc.security.browser.defaultFailureUrl:demo-signIn.jsp}")
	private String defaultFailureUrl;
	private Logger logger=LoggerFactory.getLogger(ImoocAuthenticationFailureHandler.class);
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private ObjectMapper objectMapper;//springmvc启动时已注册了这个工具类
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		logger.info("登陆失败");
		//这里设置父类的默认错误跳转页面
		super.setDefaultFailureUrl(defaultFailureUrl);
		if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(objectMapper.writeValueAsString(exception));
		}else{
			super.onAuthenticationFailure(request, response, exception);
		}
	}
	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}
	
	
}
