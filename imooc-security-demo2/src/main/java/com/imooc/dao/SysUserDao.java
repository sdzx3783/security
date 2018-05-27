package com.imooc.dao;

import org.springframework.stereotype.Repository;

import com.imooc.security.model.SysUser;
import com.sz.dao.base.BaseDao;
@Repository
public class SysUserDao extends BaseDao<SysUser>{

	@Override
	public Class getEntityClass() {
		return SysUser.class;
	}

}
