package com.sz.dao.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sz.core.page.PageBean;
import com.sz.core.page.PageList;
import com.sz.web.query.QueryFilter;

import util.BeanUtils;

public abstract class GenericDao<E, PK extends Serializable> extends BaseMyBatisDao implements IEntityDao<E, PK> {
	@Resource
	protected JdbcTemplate jdbcTemplate;

	public abstract Class getEntityClass();

	public E getById(PK primaryKey) {
		String getStatement = this.getIbatisMapperNamespace() + ".getById";
		Object object = this.getSqlSessionTemplate().selectOne(getStatement, primaryKey);
		return (E) object;
	}

	public E getUnique(String sqlKey, Object params) {
		String getStatement = this.getIbatisMapperNamespace() + "." + sqlKey;
		Object object = this.getSqlSessionTemplate().selectOne(getStatement, params);
		return (E) object;
	}

	public Object getOne(String sqlKey, Object params) {
		String statement = this.getIbatisMapperNamespace() + "." + sqlKey;
		Object object = this.getSqlSessionTemplate().selectOne(statement, params);
		return object;
	}

	public List<E> getBySqlKey(String sqlKey, Object params, PageBean pageBean) {
		String statement = this.getIbatisMapperNamespace() + "." + sqlKey;
		return this.getList(statement, params, pageBean);
	}

	public List<E> getBySqlKey(String sqlKey, QueryFilter queryFilter) {
		List list=new ArrayList();;
		if (queryFilter.getPageBean() == null) {
			list = this.getBySqlKey(sqlKey, (Object) queryFilter.getFilters());
		} else {
			list = this.getBySqlKey(sqlKey, queryFilter.getFilters(), queryFilter.getPageBean());
		}

		queryFilter.setForWeb();
		return list;
	}

	public List getBySqlKeyGenericity(String sqlKey, Object params) {
		String statement = this.getIbatisMapperNamespace() + "." + sqlKey;
		return this.getSqlSessionTemplate().selectList(statement, params);
	}

	public List<E> getBySqlKey(String sqlKey, Object params) {
		String statement = this.getIbatisMapperNamespace() + "." + sqlKey;
		return this.getSqlSessionTemplate().selectList(statement, params);
	}

	@Deprecated
	public List<?> getListBySqlKey(String sqlKey, Object params) {
		String statement = this.getIbatisMapperNamespace() + "." + sqlKey;
		return this.getSqlSessionTemplate().selectList(statement, params);
	}

	public List<E> getBySqlKey(String sqlKey) {
		String statement = this.getIbatisMapperNamespace() + "." + sqlKey;
		List list = this.getSqlSessionTemplate().selectList(statement);
		return list;
	}

	public int delById(PK id) {
		String delStatement = this.getIbatisMapperNamespace() + ".delById";
		int affectCount = this.getSqlSessionTemplate().delete(delStatement, id);
		return affectCount;
	}

	public int delBySqlKey(String sqlKey, Object params) {
		String delStatement = this.getIbatisMapperNamespace() + "." + sqlKey;
		int affectCount = this.getSqlSessionTemplate().delete(delStatement, params);
		return affectCount;
	}

	public void add(E entity) {
		String addStatement = this.getIbatisMapperNamespace() + ".add";
		
		this.getSqlSessionTemplate().insert(addStatement, entity);
	}

	public int update(E entity) {
		String updStatement = this.getIbatisMapperNamespace() + ".update";
		int affectCount1 = this.getSqlSessionTemplate().update(updStatement, entity);
		return affectCount1;
	}

	public int update(String sqlKey, Object params) {
		String updStatement = this.getIbatisMapperNamespace() + "." + sqlKey;
		int affectCount = this.getSqlSessionTemplate().update(updStatement, params);
		return affectCount;
	}

	public String getIbatisMapperNamespace() {
		return this.getEntityClass().getName();
	}

	public List<E> getList(String statementName, Object params, PageBean pageBean) {
		if (pageBean == null) {
			return this.getList(statementName, params);
		} else {
			HashMap filters = new HashMap();
			if (params != null) {
				if (params instanceof Map) {
					filters.putAll((Map) params);
				} else {
					Map rowBounds = BeanUtils.describe(params);
					filters.putAll(rowBounds);
				}
			}

			if (pageBean.isShowTotal()) {
				IbatisSql rowBounds1 = this.getIbatisSql(statementName, filters);
				this.log.info(rowBounds1.getSql());
				int list = this.jdbcTemplate.queryForObject(rowBounds1.getCountSql(), rowBounds1.getParameters(),Integer.class);
				pageBean.setTotalCount(list);
			}

			RowBounds rowBounds2 = new RowBounds(pageBean.getFirst(), pageBean.getPageSize());
			List list1 = this.getSqlSessionTemplate().selectList(statementName, filters, rowBounds2);
			PageList pageList = new PageList();
			pageList.addAll(list1);
			pageList.setPageBean(pageBean);
			return pageList;
		}
	}

	public List<E> getList(String statementName, Object params) {
		HashMap filters = new HashMap();
		if (params != null) {
			if (params instanceof Map) {
				filters.putAll((Map) params);
			} else {
				Map ibatisSql = BeanUtils.describe(params);
				filters.putAll(ibatisSql);
			}
		}

		if (this.log.isDebugEnabled()) {
			IbatisSql ibatisSql1 = this.getIbatisSql(statementName, filters);
			this.log.debug(ibatisSql1.getSql());
		}

		return this.getSqlSessionTemplate().selectList(statementName, filters);
	}

	public List<E> getList(String statementName, QueryFilter queryFilter) {
		List list = null;
		PageBean pageBean = queryFilter.getPageBean();
		Map filters = queryFilter.getFilters();
		if (pageBean != null) {
			list = this.getList(statementName, queryFilter.getFilters(), pageBean);
		} else {
			list = this.getList(statementName, (Object) filters);
		}

		return list;
	}

	public List<E> getAll() {
		String statementName = this.getIbatisMapperNamespace() + ".getAll";
		return this.getSqlSessionTemplate().selectList(statementName, (Object) null);
	}

	public List<E> getAll(QueryFilter queryFilter) {
		String statementName = this.getIbatisMapperNamespace() + ".getAll";
		List list = this.getList(statementName, queryFilter);
		queryFilter.setForWeb();
		return list;
	}

	public int insert(String sqlKey, Object params) {
		String updStatement = this.getIbatisMapperNamespace() + "." + sqlKey;
		int affectCount = this.getSqlSessionTemplate().insert(updStatement, params);
		return affectCount;
	}
}