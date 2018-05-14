package com.imooc.security.core.validate.code;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.imooc.security.core.properties.SecurityProperties;

public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{
	private SecurityProperties securityProperties;
	
	private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
	
	private Set<String> urls=new HashSet<>();
	
	private AntPathMatcher pathMatcher=new AntPathMatcher();
	
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		boolean action=false;
		for (String string : urls) {
			if(pathMatcher.match(string, request.getRequestURI())){
				action=true;
			}
		}
		if(action){
			try {
				validateCode(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return ;
			}
		}
		filterChain.doFilter(request, response);
	}
	private void validateCode(ServletWebRequest servletWebRequest) throws ValidateCodeException, ServletRequestBindingException{
		ValidateCode imageCode=(ValidateCode) sessionStrategy.getAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
		String stringParameter = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");
		if(StringUtils.isBlank(stringParameter)){
			throw new ValidateCodeException("验证码不能为空");
		}
		if(imageCode==null){
			throw new ValidateCodeException("验证码不存在");
		}
		if(imageCode.isExpire()){
			sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
			throw new ValidateCodeException("验证码已过期");
		}
		if(!StringUtils.equals(imageCode.getCode(), stringParameter)){
			throw new ValidateCodeException("验证码不匹配");
		}
		sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
	}
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		String[] splitByWholeSeparatorPreserveAllTokens = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
		for (String string : splitByWholeSeparatorPreserveAllTokens) {
			urls.add(string);
		}
		urls.add("/authentication/form");
	}
	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}
	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	public Set<String> getUrls() {
		return urls;
	}
	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}
	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}
	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}
	
	
}
