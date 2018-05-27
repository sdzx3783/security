package com.sz.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;
import util.PropUtils;



/**
 * jedis实现基类
 * 
 * @author shenyang
 */
public abstract class RedisCache{

	private static Logger logger = LoggerFactory.getLogger(RedisCache.class);
   
	private static final String INSTANT_SERVERLIST = PropUtils.getProperty("instant_cache_server_list","api"); 
	
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(); 

    private static List<JedisShardInfo> shards;
    
    private static ShardedJedisPool instant_pool;
    
    protected final ShardedJedisPool pool;
    
    protected ShardedJedis jedis;
    
    public RedisCache() {

    	 if (instant_pool==null) {
    		 init();
		 } 
   	     	         
    	 pool = instant_pool;
        
    }  
    private void init(){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(200);
		config.setMaxIdle(50);
		config.setMaxWaitMillis(3000);
		config.setTestOnBorrow(false);	
		config.setTestOnReturn(true);
		config.setTestWhileIdle(true);
    	 shards = new ArrayList<JedisShardInfo>();
    	 String[] hostDefs = INSTANT_SERVERLIST.split(",");
    	 //初始化瞬时缓存连接池
         for (String hostDef : hostDefs) {
             String[] hostAndPort = hostDef.split(":");
             shards.add(new JedisShardInfo(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
         }  
         instant_pool = new ShardedJedisPool(config, shards);
/*         //清空缓存
         for (int i = 0; i < hostDefs.length; i++) {
        	Jedis jedis = (Jedis)instant_pool.getResource().getAllShards().toArray()[i];
        	jedis.flushAll();
        	jedis.disconnect();
		 }*/
    }

	public synchronized String hget(String key,String field) {		
		acquireReadLock();	
				
		String returnValue = "";
		
		try {
			jedis = pool.getResource();             
			returnValue = jedis.hget(key, field);			
         } catch (JedisException  e) {
        	 logger.error(e.getMessage(), e);
        	 if(jedis!=null){ 
        	  pool.returnBrokenResource(jedis);
        	 }	        
        }finally{
        	pool.returnResource(jedis);
        	releaseReadLock();
        }		
		
		return returnValue;	
	}
	
	public synchronized void hset(String key,String filed,String value) {	
		acquireWriteLock();		
	    try {
			jedis = pool.getResource();				
			jedis.hset(key,filed, value);
			if (jedis.ttl(key)==-1) {
				jedis.expire(key, getExptime());
			}	
//			jedis.setex(cacheKey.getBytes(), getExptime(), (byte[])value);
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
	
	public synchronized void set(String key,String value) {	
		acquireWriteLock();		
	    try {
			jedis = pool.getResource();				
			jedis.set(key, value);
			jedis.expire(key, getExptime());
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
	public synchronized String get(String key) {		
		acquireReadLock();					
		String returnValue = "";
		
		try {
			jedis = pool.getResource();             
			returnValue = jedis.get(key);			
         } catch (JedisException  e) {
        	 logger.error(e.getMessage(), e);
        	 if(jedis!=null){ 
        	  pool.returnBrokenResource(jedis);
        	 }	        
        }finally{
        	pool.returnResource(jedis);
        	releaseReadLock();
        }		
		
		return returnValue;	
	}
	// 从缓存中删除指定 key 数据
	
	public synchronized Long hdel(String key,String... fields) {			
		 	acquireWriteLock();

		    Long reult = null;
			
		    try {
				jedis = pool.getResource();
				reult = jedis.hdel(key, fields);
	         } catch (JedisException  e) {
	        	 logger.error(e.getMessage(), e);
	        	 if(jedis!=null){ 
	        	  pool.returnBrokenResource(jedis);
	        	 }	        
	         }finally{
	         	pool.returnResource(jedis);
			    releaseWriteLock();
	         }		    	         		
		    
		    return reult;
		    
		   
	}	
	public synchronized Long del(String key) {			
	 	acquireWriteLock();

	    Long reult = null;
		
	    try {
			jedis = pool.getResource();
			reult = jedis.del(key);
         } catch (JedisException  e) {
        	 logger.error(e.getMessage(), e);
        	 if(jedis!=null){ 
        	  pool.returnBrokenResource(jedis);
        	 }	        
         }finally{
         	pool.returnResource(jedis);
		    releaseWriteLock();
         }		    	         		
	    
	    return reult;
	    
	   
}	
	public synchronized Long hincr(String key,String field) {	
		acquireWriteLock();		
	    try {
			jedis = pool.getResource();				
			return jedis.hincrBy(key, field, 1L);
         } catch (JedisException  e) {
        	 logger.error(e.getMessage(), e);
        	 if(jedis!=null){ 
        	  pool.returnBrokenResource(jedis);
        	 }	        
         }finally{
         	pool.returnResource(jedis);
         	releaseWriteLock();
         }
		return -1L;      		    	    
	}
	public synchronized Map<String, String> hgetAll(String key) {	
		acquireWriteLock();		
	    try {
			jedis = pool.getResource();				
			return jedis.hgetAll(key);
         } catch (JedisException  e) {
        	 logger.error(e.getMessage(), e);
        	 if(jedis!=null){ 
        	  pool.returnBrokenResource(jedis);
        	 }	        
         }finally{
         	pool.returnResource(jedis);
         	releaseWriteLock();
         }
		return null;      		    	    
	}
	
	public ReadWriteLock getReadWriteLock() {
		return this.readWriteLock;
	}
	
	@PreDestroy
	protected void stop(){

			pool.destroy();
		
	}
	
	protected void acquireReadLock() {
	    getReadWriteLock().readLock().lock();
	  }

	protected void releaseReadLock() {
	    getReadWriteLock().readLock().unlock();
	  }

	protected void acquireWriteLock() {
		getReadWriteLock().writeLock().lock();
	}

	protected void releaseWriteLock() {
 		getReadWriteLock().writeLock().unlock();
 	}
	/**
	 * @return the timeout
	 */
	protected abstract long getTimeout();

	/**
	 * @param timeout the timeout to set
	 */
	protected abstract void setTimeout(long timeout);

	/**
	 * @return the exptime
	 */
	protected abstract int getExptime() ;

	/**
	 * @param exptime the exptime to set
	 */
	protected abstract void setExptime(int exptime);
	
	/**
	 * @return the serverList
	 */
//	abstract String getServerList();

	/**
	 * @param serverList the serverList to set
	 */
//	abstract void setServerList(String serverList);
	
}
