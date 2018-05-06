<%@page import="com.imooc.security.core.validate.code.ValidateCodeException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.springframework.security.authentication.AuthenticationServiceException"%>
<%@page import="org.springframework.security.authentication.AccountExpiredException"%>
<%@page import="org.springframework.security.authentication.DisabledException"%>
<%@page import="org.springframework.security.authentication.LockedException"%>
<%@page import="com.imooc.security.core.validate.code.ValidateCodeException"%>
<%@page import="org.springframework.security.authentication.BadCredentialsException"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.springframework.security.web.WebAttributes"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>自定义登录页</title>
</head>
<body>
	<form action="/authentication/form" method="post">
		<table>
		<thead>Demo 登陆页</thead>
			<tr>
				<td>用户名:</td>
				<td><input type="text" name="username" value="admin"/></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input type="password"  name="password" value="1"/></td>
			</tr>
			<tr>
				<td>验证码:</td>
				<td><input type="text"  name="imageCode"/>
					<img src="/code/image"/></td>
			</tr>
			<tr>
				<td colspan="2"><button type="submit">登录</button></td>
			</tr>
		
		</table>
		<div>
			<%
				Object loginError=(Object)request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
				
				if(loginError!=null ){
					String msg="";
					if(loginError instanceof BadCredentialsException){
						msg="密码输入错误";
					}
					else if(loginError instanceof AuthenticationServiceException){
						AuthenticationServiceException ex=(AuthenticationServiceException)loginError;
						msg=ex.getMessage();
					}else if(loginError instanceof ValidateCodeException){
						msg="验证码错误";
					}
					else{
						msg=loginError.toString();
					}
				%>
				<%=msg %>
				<%
				//request.getSession().removeAttribute("validCodeEnabled");//删除需要验证码的SESSION
				request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);//删除登录失败信息
				}
				%>
		</div>
	
	</form>
</body>
</html>