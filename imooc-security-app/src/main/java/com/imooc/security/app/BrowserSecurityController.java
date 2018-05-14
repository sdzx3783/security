package com.imooc.security.app;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.model.SimpleResponse;

@RestController
public class BrowserSecurityController {
	private Logger logger=LoggerFactory.getLogger(BrowserSecurityController.class);
	private RequestCache requestCache=new HttpSessionRequestCache();
	
	private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
	@Autowired
	private SecurityProperties securityProperties;
	/**
	 * 当需要身份认证时，需要跳转到这里
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/authentication/require")
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthentication(HttpServletRequest request,HttpServletResponse response) throws IOException{
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if(savedRequest!=null){
			String redirectUrl = savedRequest.getRedirectUrl();
			logger.info("引发的跳转url是："+redirectUrl);
			if(StringUtils.endsWith(redirectUrl, ".html")|| StringUtils.endsWith(redirectUrl, ".jsp")){
				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
			}
		}
		return new SimpleResponse("访问的服务需要身份认证，请引导用户到登陆页!");
	}
}
