package com.sz.dao.base;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IbatisSql
{
  private String sql;
  private Object[] parameters;
  private Class resultClass;
  
  public Class getResultClass()
  {
    return this.resultClass;
  }
  
  public void setResultClass(Class resultClass)
  {
    this.resultClass = resultClass;
  }
  
  public void setSql(String sql)
  {
    this.sql = sql;
  }
  
  public String getSql()
  {
    return this.sql;
  }
  
  public void setParameters(Object[] parameters)
  {
    this.parameters = parameters;
  }
  
  public Object[] getParameters()
  {
    return this.parameters;
  }
  
  public String getCountSql()
  {
    String sqlCount = this.sql;
    sqlCount = "select count(*) amount from (" + sqlCount + ") A";
    return sqlCount;
  }
}
