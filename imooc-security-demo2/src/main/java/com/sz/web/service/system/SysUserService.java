package com.sz.web.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.dao.SysUserDao;
import com.imooc.security.model.SysUser;
import com.sz.dao.base.IEntityDao;
import com.sz.service.BaseService;
@Service
public class SysUserService extends BaseService<SysUser> {
	@Autowired
	private SysUserDao sysUserDao;
	@Override
	protected IEntityDao<SysUser, Long> getEntityDao() {
		return sysUserDao;
	}
	public SysUser getByAccount(String username) {
		SysUser unique = sysUserDao.getUnique("getByAccount", username);
		return unique;
	}
}
