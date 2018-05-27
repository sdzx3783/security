<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <!DOCTYPE html>
 <html>
 <head>
     <title>Java后端WebSocket的Tomcat实现</title>
     <link href="/styles/blue/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	  	<script type="text/javascript" src="/js/jquery/jquery-3.3.1.min.js"></script>
  		<script src="/js/jquery/bootstrap.min.js"></script>
  		<style type="text/css">
  			.page-header {
			    padding-bottom: 9px;
			    margin: 20px 10px 20px;
			    border-bottom: 1px solid #eee;
			    padding-left: 10px;
			    padding-right: 10px;
			}
  		</style>
 </head>
 <body>
	 <div class="page-header">
	  <h1>Welcome to Sammy Chat Room<small style="float:right"><security:authentication property="principal.username" /></small></h1>
	</div>
	<div class="container">
		<div class="row">
			<div class="panel panel-default col-md-6">
			  <div class="panel-heading">聊天</div>
			  <div class="panel-body">
			    
			  </div>
			</div>
			
			<div class="panel panel-default col-md-6">
			  <div class="panel-heading">在线用户(人数:<span id="ucount">${fn:length(onLineUsers)}</span>)</div>
			  <div class="panel-body">
				<ul class="list-group onlineusers">
					<c:forEach items="${onLineUsers }" var="user">
						 <li class="list-group-item" data-id="${user.key}">${user.value.username }</li>
					</c:forEach>
				</ul>
			  </div>
			</div>
		</div>
	</div>
	
	
     <input id="text" type="text"/>
     <button onclick="send()">发送消息</button>
     <hr/>
     <button onclick="closeWebSocket()">关闭WebSocket连接</button>
     <hr/>
     <div id="message"></div>
 </body>
 
 <script type="text/javascript">
     var websocket = null;
     //判断当前浏览器是否支持WebSocket
     if ('WebSocket' in window) {
         websocket = new WebSocket("ws://localhost:8098/websocket/"+'<security:authentication property="principal.account" />');
     }
     else {
         alert('当前浏览器 Not support websocket')
     }
 
     //连接发生错误的回调方法
     websocket.onerror = function () {
         setMessageInnerHTML("WebSocket连接发生错误");
     };
 
     //连接成功建立的回调方法
     websocket.onopen = function () {
         setMessageInnerHTML("WebSocket连接成功");
     }
 
     //接收到消息的回调方法
     websocket.onmessage = function (event) {
    	 console.log(event);
         setMessageInnerHTML(event.data);
         var msg=eval("("+event.data+")");
         if(msg.msgType==1){
        	 //用户上线通知
        	 if($(".onlineusers li[data-id='"+msg.userid+"']").length<=0){
        		$(".onlineusers").append('<li class="list-group-item" data-id="'+msg.userid+'">'+msg.username+'</li>');
        		$("#ucount").text(parseInt($("#ucount").text())+1);
        	 }
         }else if(msg.msgType==2){
        	//用户下线通知
        	if($(".onlineusers li[data-id='"+msg.userid+"']").length>0){
        		$(".onlineusers li[data-id='"+msg.userid+"']").remove();
        		$("#ucount").text(parseInt($("#ucount").text())-1);
        	}
         }
     }
 
     //连接关闭的回调方法
     websocket.onclose = function () {
         setMessageInnerHTML("WebSocket连接关闭");
     }
 
     //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
     window.onbeforeunload = function () {
         closeWebSocket();
     }
 
     //将消息显示在网页上
     function setMessageInnerHTML(innerHTML) {
         document.getElementById('message').innerHTML += innerHTML + '<br/>';
     }
 
     //关闭WebSocket连接
     function closeWebSocket() {
         websocket.close();
     }
 
     //发送消息
     function send() {
         var message = document.getElementById('text').value;
         websocket.send(message);
     }
 </script>
 </html>