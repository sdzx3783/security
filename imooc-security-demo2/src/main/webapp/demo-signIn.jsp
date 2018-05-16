<%@page
	import="com.imooc.security.core.validate.code.ValidateCodeException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="org.springframework.security.authentication.AuthenticationServiceException"%>
<%@page
	import="org.springframework.security.authentication.AccountExpiredException"%>
<%@page
	import="org.springframework.security.authentication.DisabledException"%>
<%@page
	import="org.springframework.security.authentication.LockedException"%>
<%@page
	import="com.imooc.security.core.validate.code.ValidateCodeException"%>
<%@page
	import="org.springframework.security.authentication.BadCredentialsException"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.springframework.security.web.WebAttributes"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>自定义登录页</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="/styles/blue/css/bootstrap/bootstrap.min.css"
	rel="stylesheet">
<script type="text/javascript" src="/js/jquery/jquery-3.3.1.min.js"></script>
<script src="/js/jquery/bootstrap.min.js"></script>
<style type="text/css">

	div.main,div.foot{
	 	margin:auto；
		width: 50%;
		text-align:center;
		
	}
	div.header,div.tab-content{
		margin:0 150px；
		width:350px;
	}
	ul{
		display: inline-block;
	}
</style>
</head>

<body>
<div class="main">
	<div class="header">
		<ul id="myTab" class="nav nav-tabs">
			<li class="active"><a href="#home" data-toggle="tab">密码登陆 </a></li>
			<li><a href="#mobile" data-toggle="tab">手机登陆</a></li>
		</ul>
	</div>
	<div id="myTabContent"  class="tab-content panel-default">
		<div class="tab-pane fade in active" id="home">
			<form class="form-horizontal" action="/authentication/form"
				method="post">
				<fieldset>
					<div id="legend" class="">
						<legend class="">密码登陆</legend>
					</div>
					<div class="control-group">
						<label class="control-label">用户名:</label>
						<div class="controls">
							<input type="text" name="username" placeholder="请输入名称"
								value="admin" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">密码:</label>
						<div class="controls">
							<input type="password" name="password" value="1" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">验证码:</label>
						<div class="controls">
							<input type="text" name="imageCode" /> <img src="/code/image" />
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn-primary">登录</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="tab-pane fade" id="mobile">
			<form class="form-horizontal" action="/authentication/mobile"
				method="post">
				<fieldset>
					<div id="legend" class="">
						<legend class="">手机号登陆</legend>
					</div>
					<div class="control-group">
						<label class="control-label">手机号:</label>
						<div class="controls">
							<input type="text" name="mobile" placeholder="请输入手机号"
								value="" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">验证码:</label>
						<div class="controls">
							<input type="password" name="password" value="" />
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn-primary">登录</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
	</div>
	<div class="foot">
		<%
			Object loginError = (Object) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

			if (loginError != null) {
				String msg = "";
				if (loginError instanceof BadCredentialsException) {
					msg = "密码输入错误";
				} else if (loginError instanceof AuthenticationServiceException) {
					AuthenticationServiceException ex = (AuthenticationServiceException) loginError;
					msg = ex.getMessage();
				} else if (loginError instanceof ValidateCodeException) {
					msg = "验证码错误";
				} else {
					msg = loginError.toString();
				}
		%>
		<%=msg%>
		<%
			//request.getSession().removeAttribute("validCodeEnabled");//删除需要验证码的SESSION
				request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);//删除登录失败信息
			}
		%>
	</div>
</div>
</body>
</html>