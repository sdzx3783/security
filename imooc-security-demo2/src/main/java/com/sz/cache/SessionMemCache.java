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

import org.springframework.stereotype.Component;

import util.PropUtils;




/**
 * @ClassName: InstantMemCache 
 * @Description: TODO
 */
@Component("sessionMemCache")
public class SessionMemCache extends RedisCache{	
    
	private static long timeout = 3000;
	
	private int exptime = Integer.parseInt(PropUtils.getProperty("session_exptime","api"));

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

	public static void main(String[] args) throws InterruptedException {
		SessionMemCache mem = new SessionMemCache();
		mem.set("123", "hahaha");
		Thread.sleep(1500);
		System.out.println(mem.get("123"));
		mem.set("123", "hehe");
		Thread.sleep(5000);
		System.out.println(mem.get("123"));
		Thread.sleep(15000);
		System.out.println(mem.get("123"));
	}
}
