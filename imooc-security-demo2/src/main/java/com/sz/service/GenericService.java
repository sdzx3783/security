package com.sz.service;


import java.io.Serializable;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sz.core.page.PageBean;
import com.sz.dao.base.IEntityDao;
import com.sz.web.query.QueryFilter;

import util.BeanUtils;

public abstract class GenericService<E, PK extends Serializable> {
	protected Logger logger = LoggerFactory.getLogger(GenericService.class);

	protected abstract IEntityDao<E, PK> getEntityDao();

	public void add(E entity) {
		this.getEntityDao().add(entity);
	}

	public void delById(PK id) {
		this.getEntityDao().delById(id);
	}

	public void delByIds(PK[] ids) {
		if (!BeanUtils.isEmpty(ids)) {
			Serializable[] arr$ = ids;
			int len$ = ids.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				Serializable p = arr$[i$];
				this.delById((PK) p);
			}

		}
	}

	public void update(E entity) {
		this.getEntityDao().update(entity);
	}

	public E getById(PK id) {
		return (E) this.getEntityDao().getById(id);
	}

	public List<E> getList(String statatementName, PageBean pb) {
		List list = this.getEntityDao().getList(statatementName, pb);
		return list;
	}

	public List<E> getAll() {
		return this.getEntityDao().getAll();
	}

	public List<E> getAll(QueryFilter queryFilter) {
		return this.getEntityDao().getAll(queryFilter);
	}
}