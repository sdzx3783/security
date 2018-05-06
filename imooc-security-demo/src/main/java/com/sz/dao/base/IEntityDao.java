package com.sz.dao.base;


import java.io.Serializable;
import java.util.List;

import com.sz.core.page.PageBean;
import com.sz.web.query.QueryFilter;

public interface IEntityDao<E, PK extends Serializable> {
	void add(E arg0);

	int delById(PK arg0);

	int update(E arg0);

	Object getById(PK arg0);

	List<E> getList(String arg0, Object arg1);

	List<E> getList(String arg0, Object arg1, PageBean arg2);

	List<E> getAll();

	List<E> getAll(QueryFilter arg0);

	E getUnique(String arg0, Object arg1);
}