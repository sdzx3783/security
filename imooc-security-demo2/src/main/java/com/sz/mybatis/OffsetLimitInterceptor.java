package com.sz.mybatis;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sz.mybatis.dialect.Dialect;


@Intercepts({@org.apache.ibatis.plugin.Signature(type=org.apache.ibatis.executor.Executor.class, method="query", args={MappedStatement.class, Object.class, RowBounds.class, org.apache.ibatis.session.ResultHandler.class})})
public class OffsetLimitInterceptor
  implements Interceptor
{
  private static Logger logger = LoggerFactory.getLogger(OffsetLimitInterceptor.class);
  static int MAPPED_STATEMENT_INDEX = 0;
  static int PARAMETER_INDEX = 1;
  static int ROWBOUNDS_INDEX = 2;
  static int RESULT_HANDLER_INDEX = 3;
  
  Dialect dialect;
  

  private static String getSql(String sql)
  {
    return sql.trim().replaceAll("(?si)\\s+", " ");
  }
  
  public Object intercept(Invocation invocation)
    throws Throwable
  {
    Object[] queryArgs = invocation.getArgs();
    MappedStatement ms = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];
    Object parameter = queryArgs[PARAMETER_INDEX];
    RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX];
    

    int offset = rowBounds.getOffset();
    
    int limit = rowBounds.getLimit();
    


    BoundSql boundSql = ms.getBoundSql(parameter);
    StringBuffer bufferSql = new StringBuffer(boundSql.getSql().trim());
    if (bufferSql.lastIndexOf(";") == bufferSql.length() - 1) {
      bufferSql.deleteCharAt(bufferSql.length() - 1);
    }
    
    String sql = getSql(bufferSql.toString().trim());
    


    if ((dialect.supportsLimit()) && ((offset != 0) || (limit != Integer.MAX_VALUE)))
    {
      if (dialect.supportsLimitOffset()) {
        sql = dialect.getLimitString(sql, offset, limit);
      } else {
        sql = dialect.getLimitString(sql, 0, limit);
      }
      queryArgs[ROWBOUNDS_INDEX] = new RowBounds(0, Integer.MAX_VALUE);
    }
    
    queryArgs[MAPPED_STATEMENT_INDEX] = copyFromNewSql(ms, boundSql, sql);
    return invocation.proceed();
  }
  
  private MappedStatement copyFromNewSql(MappedStatement ms, BoundSql boundSql, String sql)
  {
    BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, sql);
    return copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
  }
  
  private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql)
  {
    BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
    for (ParameterMapping mapping : boundSql.getParameterMappings()) {
      String prop = mapping.getProperty();
      if (boundSql.hasAdditionalParameter(prop)) {
        newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
      }
    }
    return newBoundSql;
  }
  
  private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource)
  {
    MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
    
    builder.resource(ms.getResource());
    builder.fetchSize(ms.getFetchSize());
    builder.statementType(ms.getStatementType());
    builder.keyGenerator(ms.getKeyGenerator());
    if ((ms.getKeyProperties() != null) && (ms.getKeyProperties().length != 0)) {
      StringBuffer keyProperties = new StringBuffer();
      for (String keyProperty : ms.getKeyProperties()) {
        keyProperties.append(keyProperty).append(",");
      }
      keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
      builder.keyProperty(keyProperties.toString());
    }
    

    builder.timeout(ms.getTimeout());
    

    builder.parameterMap(ms.getParameterMap());
    

    builder.resultMaps(ms.getResultMaps());
    builder.resultSetType(ms.getResultSetType());
    

    builder.cache(ms.getCache());
    builder.flushCacheRequired(ms.isFlushCacheRequired());
    builder.useCache(ms.isUseCache());
    
    return builder.build();
  }
  
  public Object plugin(Object target)
  {
    return Plugin.wrap(target, this);
  }
  
  public void setProperties(Properties properties)
  {
    
	String type = properties.getProperty("jdbc.dbType");
	if(StringUtils.isEmpty(type)) {
		throw new RuntimeException("mybatis 分页插件未配置jdbc.type属性");
	}
	String property = properties.getProperty("Dialect."+type);
	if(StringUtils.isEmpty(property)) {
		throw new RuntimeException("mybatis 分页插件未配置Dialect."+type+"属性");
	}
    try {
      setDialect((Dialect)Class.forName(property).newInstance());
    } catch (Exception e) {
      throw new RuntimeException("cannot create dialect instance by dialectClass:" + property, e);
    }
  }
  
  public static class BoundSqlSqlSource implements SqlSource {
    BoundSql boundSql;
    
    public BoundSqlSqlSource(BoundSql boundSql) { this.boundSql = boundSql; }
    
    public BoundSql getBoundSql(Object parameterObject) {
      return boundSql;
    }
  }
  
  public void setDialect(Dialect dialect) {
    logger.debug("dialectClass: {} ", dialect.getClass().getName());
    this.dialect = dialect;
  }
}
