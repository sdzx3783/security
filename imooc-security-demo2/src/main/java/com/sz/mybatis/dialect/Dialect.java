package com.sz.mybatis.dialect;

public class Dialect
{
	public boolean supportsLimit()
   {
     return false;
   }
   
   public boolean supportsLimitOffset() {
     return supportsLimit();
   }
   
 
   public String getLimitString(String sql, int offset, int limit)
   {
     return getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
   }
   

   public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder)
   {
     throw new UnsupportedOperationException("paged queries not supported");
   }
   
 
   public String getCountSql(String sql)
   {
     return "select count(1) from (" + sql + ") tmp_count";
   }
   
}