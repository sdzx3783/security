package com.sz.service.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.sz.cache.RedisAtomicClient;
import com.sz.cache.RedisLock;

@Service
public class TestRedisLockService {
	private static final String REDIS_PRODUCT_STOCK_LOCK_PREKEY="productStockLock:";
	private static final String REDIS_PRODUCT_STOCK_KEY="productStock:";
	private static final Logger log=LoggerFactory.getLogger(TestRedisLockService.class);
	@Autowired
	private StringRedisTemplate redisTemplate;
	public Map<String,Long> decreaseStock(Long proId,Long num) {
		Object object = redisTemplate.opsForValue().get(REDIS_PRODUCT_STOCK_KEY+proId);
		if(object==null || (Long.valueOf((String)object))<=0){
			throw new RuntimeException("该商品没有库存了！");
		}
		Long realnum=Long.valueOf((String)object);
		if(realnum<num) {
			throw new RuntimeException("该商品库存不足！库存数量为："+realnum);
		}
		RedisAtomicClient rc=new RedisAtomicClient(redisTemplate);
		RedisLock lock = rc.getLock(REDIS_PRODUCT_STOCK_LOCK_PREKEY+proId, 120, 3, 1000);
		if(lock!=null) {
			log.info("获取锁："+REDIS_PRODUCT_STOCK_LOCK_PREKEY+proId+" 成功！");
			try {
				//再次查询库存
				Object object2 = redisTemplate.opsForValue().get(REDIS_PRODUCT_STOCK_KEY+proId);
				if(object2==null || (Long.valueOf((String)object2))<=0){
					throw new RuntimeException("该商品没有库存了！");
				}
				Long realnum2=Long.valueOf((String)object);
				if(realnum2<num) {
					throw new RuntimeException("该商品库存不足！库存数量为："+realnum);
				}
				
				Long incrBy = rc.incrBy(REDIS_PRODUCT_STOCK_KEY+proId, -num);
				Thread.sleep(new Random().nextInt(2000));//模拟耗时操作
				HashMap<String,Long>rt=new HashMap<>();
				rt.put("proId", proId);
				rt.put("stock", incrBy);
				return rt;
			}catch (InterruptedException e) {
				log.error("interrupted error:", e);
			}catch (Exception e) {
				log.error("error:", e);
				throw new RuntimeException("服务繁忙，请稍后再试!");
			}finally {
				lock.unlock();
			}
		}else {
			log.info("获取锁："+REDIS_PRODUCT_STOCK_LOCK_PREKEY+proId+" 失败！");
			throw new RuntimeException("服务繁忙，i请稍后再试!");
		}
		throw new RuntimeException("服务繁忙，请稍后再试!");
	}
}
