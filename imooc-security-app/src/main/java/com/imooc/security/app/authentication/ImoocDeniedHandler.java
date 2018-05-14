package com.imooc.security.app.authentication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class ImoocDeniedHandler implements AccessDeniedHandler{
	private String accessDeniedUrl="/error/401.html";

	public String getAccessDeniedUrl() {
		return accessDeniedUrl;
	}
	
	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		String requestURI = request.getRequestURI();
		
		request.setAttribute("ex", accessDeniedException);
		try{
			if(requestURI.endsWith(".html") || requestURI.endsWith(".jsp") || requestURI.endsWith(".ht")) {
				request.getRequestDispatcher(accessDeniedUrl).forward(request, response);
			}else{
				response.setStatus(401);
				PrintWriter writer = response.getWriter();
				writer.print("\"status\":401,\"msg\":\"权限不足\"");
			}
		}
		catch(Exception e){
			
		}
		return ;
	}
}
