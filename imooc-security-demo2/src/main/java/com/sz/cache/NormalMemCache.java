/**   
 * @Title: InstantMemCache.java 
 * @Package net.makshi.data.common 
 * @Description: TODO
 * @author Sherwin  
 * @date 2012-4-17 下午2:39:56 
 * @version V1.0
 * @Copyright (c)2012  MaiShi technology Co.Ltd. 
 */
package com.sz.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.exceptions.JedisException;



/**
 * @ClassName: InstantMemCache 
 * @Description: TODO
 */
@Component("normalMemCache")
public class NormalMemCache extends RedisCache{	
	
	private static Logger logger = LoggerFactory.getLogger(NormalMemCache.class);
    
	private static long timeout = 3000;
	
	private int exptime = 300;//5分钟

	/**
	 * @return the timeout
	 */
	public long getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the exptime
	 */
	public int getExptime() {
		return exptime;
	}

	/**
	 * @param exptime the exptime to set
	 */
	public void setExptime(int exptime) {
			this.exptime = exptime;		
	}
	
	public synchronized void set(String key,String value,int exptime) {	
		acquireWriteLock();		
	    try {
			jedis = pool.getResource();				
			jedis.set(key, value);
			jedis.expire(key, exptime);
         } catch (JedisException  e) {
        	 logger.error(e.getMessage(), e);
        	 if(jedis!=null){ 
        	  pool.returnBrokenResource(jedis);
        	 }	        
         }finally{
         	pool.returnResource(jedis);
         	releaseWriteLock();
         }         		    	    

	}

	public static void main(String[] args) throws InterruptedException {
		NormalMemCache mem = new NormalMemCache();
		mem.hset("123", "ha","agfg");
		System.out.println(mem.jedis.ttl("123"));
		Thread.sleep(5000);		
		System.out.println(mem.hget("123","ha"));
		System.out.println(mem.jedis.ttl("123"));
		mem.hset("123", "ha","a");
		Thread.sleep(5000);		
		System.out.println(mem.jedis.ttl("123"));
	}
}
