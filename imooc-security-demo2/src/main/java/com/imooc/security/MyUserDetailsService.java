package com.imooc.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import com.imooc.security.model.SysUser;
@Service
public class MyUserDetailsService implements UserDetailsService,SocialUserDetailsService {

	private Logger logger=LoggerFactory.getLogger(MyUserDetailsService.class);
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("表单登录用户名:"+username);
		return new SysUser(username, passwordEncoder.encode("1"));

	}

	@Override
	public SocialUserDetails loadUserByUserId(String userid) throws UsernameNotFoundException {
		logger.info("社交登录用户ID:"+userid);
		return new SysUser(userid, passwordEncoder.encode("1"));
	}

	

}
