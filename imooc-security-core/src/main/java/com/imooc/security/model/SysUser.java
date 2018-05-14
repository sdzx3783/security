package com.imooc.security.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;

public class SysUser implements UserDetails,SocialUserDetails{

	private static final long serialVersionUID = -6529304229285809133L;

	private String username;
	
	private String password;
	
	public SysUser(String username,String password) {
		this.username=username;
		this.password=password;
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> rtnList= new ArrayList<GrantedAuthority>();
		//授权App服务器需要一个ROLE_USER角色才能支持授权
		rtnList.add(new SimpleGrantedAuthority("ROLE_USER"));
		return rtnList;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUserId() {
		return null;
	}


}
