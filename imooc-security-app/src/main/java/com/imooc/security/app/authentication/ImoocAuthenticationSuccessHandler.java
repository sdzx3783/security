package com.imooc.security.app.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.security.core.properties.LoginType;
import com.imooc.security.core.properties.SecurityProperties;
@Component("imoocAuthenticationSuccessHandler")
public class ImoocAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Value("${imooc.security.browser.defaultTargetUrl:index.jsp}")
	private String defaultTargetUrl;
	@Autowired
	private SecurityProperties securityProperties;
	private Logger logger=LoggerFactory.getLogger(ImoocAuthenticationSuccessHandler.class);
	@Autowired
	private ObjectMapper objectMapper;//springmvc启动时已注册了这个工具类
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("登陆成功!");
		super.setDefaultTargetUrl(defaultTargetUrl);
		if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(objectMapper.writeValueAsString(authentication));
		}else{
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}

}
