package com.sz.dao.base.test;

import org.springframework.stereotype.Repository;

import com.sz.dao.base.BaseDao;
import com.sz.model.Test;
@Repository
public class TestDao extends BaseDao<Test> {

	@Override
	public Class getEntityClass() {
		return Test.class;
	}

}
