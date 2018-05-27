package com.sz.web.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.imooc.security.browser.authentication.event.UserLoginEvent;

@Service
public class UserLoginEventListener implements ApplicationListener<UserLoginEvent>{

	@Override
	public void onApplicationEvent(UserLoginEvent event) {
		OnlineUser onlineUser=new OnlineUser();
		onlineUser.setUserId((Long) event.getSource());
		onlineUser.setUsername(event.getSysUser().getUsername());
		UserSessionListener.addOnlineUser(onlineUser);
		
	}

}
