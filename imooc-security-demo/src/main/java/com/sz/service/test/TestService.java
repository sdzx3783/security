package com.sz.service.test;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.web.util.DateUtil;
import com.sz.dao.base.IEntityDao;
import com.sz.dao.base.test.TestDao;
import com.sz.model.Test;
import com.sz.service.BaseService;
@Service
public class TestService extends BaseService<Test> {

	@Resource
	private TestDao dao;

	@Override
	protected IEntityDao<Test, Long> getEntityDao() {
		return dao;
	}
	//2.事务注解详解
	//默认遇到throw new RuntimeException(“…”);会回滚 
	//需要捕获的throw new Exception(“…”);不会回滚
	@Transactional(propagation=Propagation.REQUIRED)
	public void testTransaction(Test t) throws Exception {
		t=new Test();
		t.setName("test:"+DateUtil.formatEnDate(new Date()));
		dao.add(t);
		throw new RuntimeException("自定义异常!");
	}
}
