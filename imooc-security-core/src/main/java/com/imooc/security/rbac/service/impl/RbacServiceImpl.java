/**
 * 
 */
package com.imooc.security.rbac.service.impl;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.imooc.security.model.SysUser;
import com.imooc.security.rbac.service.RbacService;

@Service("rbacService")
public class RbacServiceImpl implements RbacService {
	private Logger log=LoggerFactory.getLogger(RbacServiceImpl.class);
	//private AntPathMatcher antPathMatcher = new AntPathMatcher();

	
	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		boolean hasPermission = false;
		if(principal instanceof SysUser) {
			SysUser sysUser=(SysUser) principal;
			Collection<? extends GrantedAuthority> authorities = sysUser.getAuthorities();
			StringBuffer sb=new StringBuffer("权限校验: 用户名:"+sysUser.getUsername()+"用户具备的权限:");
			for (GrantedAuthority grantedAuthority : authorities) {
				sb.append(grantedAuthority.getAuthority());
				sb.append(",");
			}
			hasPermission=true;
			
			log.info(sb.substring(0, sb.length()));
		}else{
			log.error("权限校验不通过：未认证!");
		}

		return hasPermission;
	}

}
