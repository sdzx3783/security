package com.sz.web.listener;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import com.imooc.security.browser.util.AppUtil;
import com.imooc.security.browser.util.ContextUtil;
import com.imooc.security.model.SysUser;
import com.sz.web.controller.socket.WebSocket;
import com.sz.web.service.system.SysUserService;


/**
 * 监听用户登录事件和会话过期事件。
 * 管理在线用户情况。
 * @author csx
 *
 */
//使用spring-session 无法在使用该方式实现在线用户统计的功能 需要配置SessionEventHttpSessionListenerAdapter
//@WebListener
public class UserSessionListener implements HttpSessionAttributeListener,HttpSessionListener{
	private static Map<Long,OnlineUser> onlineUserCache=new ConcurrentHashMap<>();
	private static final Logger logger=LoggerFactory.getLogger(UserSessionListener.class);
	/**
	 * 获取在线用户
	 * @return
	 */
	public static Map<Long ,OnlineUser> getOnLineUsers(){
		return onlineUserCache;
	}
	
	/**
	 * 添加在线用户。
	 * @param onlineUser
	 */
	public static void addOnlineUser(OnlineUser onlineUser){
		onlineUserCache.put(onlineUser.getUserId(), onlineUser);
		Map<String, WebSocket> clients = WebSocket.getClients();
		for (Entry<String, WebSocket> iterable_element : clients.entrySet()) {
			WebSocket ws = iterable_element.getValue();
			ws.session.getAsyncRemote().sendText("{\"msgType\":\"1\",\"userid\":\""+onlineUser.getUserId()+"\",\"username\":\""+onlineUser.getUsername()+"\"}");//用户上线消息推动
		}
	}
	
	/**
	 * 删除在线用户。
	 * @param userId
	 */
	public static void removeUser(OnlineUser onlineUser){
		onlineUserCache.remove(onlineUser.getUserId());
		Map<String, WebSocket> clients = WebSocket.getClients();
		for (Entry<String, WebSocket> iterable_element : clients.entrySet()) {
			WebSocket ws = iterable_element.getValue();
			ws.session.getAsyncRemote().sendText("{\"msgType\":\"2\",\"userid\":\""+onlineUser.getUserId()+"\",\"username\":\""+onlineUser.getUsername()+"\"}");//用户上线消息推动
		}
	}
	
	
	/**
	 * 进入系统,添加在线用户
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		if(!"SPRING_SECURITY_LAST_USERNAME".equals(event.getName())) return;
		SysUser user=(SysUser) ContextUtil.getCurrentUser();
		if(user==null){
			return;
		}
		logger.info("用户上线: "+event.getName());
		OnlineUser onlineUser=new OnlineUser();
		
		onlineUser.setUserId(user.getUserid());
		onlineUser.setUsername(user.getUsername());
		
		addOnlineUser(onlineUser);
	}

	/**
	 * 退出系统，或者系统超时时+-删除在线用户
	 */
	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		if("SPRING_SECURITY_LAST_USERNAME".equals(event.getName())){
			logger.info("用户下线: "+event.getName());
			SysUser	user = AppUtil.getBean(SysUserService.class).getByAccount((String)event.getValue());
			if(user!=null){
				//removeUser(user.getUserid());
			}
		
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		logger.info("session 属性替换"+event.getName());
		
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		//无法监听用户上线的动作 再登陆成功处操作
		Object attribute = se.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		if(attribute!=null) {
			
		}
		logger.info("session create");
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		Object attribute = se.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		if(attribute!=null && attribute instanceof SecurityContext) {
			Authentication authentication = ((SecurityContext)attribute).getAuthentication();
			if(authentication!=null && authentication.getPrincipal()!=null) {
				if(authentication.getPrincipal() instanceof SysUser) {
					SysUser sysUser=(SysUser) authentication.getPrincipal();
					OnlineUser onlineUser=new OnlineUser();
					onlineUser.setUserId(sysUser.getUserid());
					onlineUser.setUsername(sysUser.getUsername());
					removeUser(onlineUser);
					logger.info("用户下线："+sysUser.getUsername());
				}
			}
		}
		
	}
}
