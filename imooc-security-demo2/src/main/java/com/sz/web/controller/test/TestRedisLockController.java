package com.sz.web.controller.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sz.service.test.TestRedisLockService;
import com.sz.web.ResultMessage;

@RestController
@RequestMapping("/anonymous/")
public class TestRedisLockController {
	@Autowired
	private TestRedisLockService redisLockService;
	private static ConcurrentLinkedQueue<Map<String,Long>> successRecord=new ConcurrentLinkedQueue<>();
	@GetMapping
	@RequestMapping("seckill/{proid}/{num}")
	public  ResultMessage seckillProject(HttpServletRequest request,@PathVariable(name="proid",required=true)Long proid,@PathVariable(name="num",required=true)Long num, HttpServletResponse response) throws Exception{
		if(num<=0) {
			return new ResultMessage(ResultMessage.Fail, "请求参数错误：秒杀数量不能比0小");
		}else{
			try {
				long num2 = new Random().nextInt(5)+1L;
				Map<String, Long> decreaseStock = redisLockService.decreaseStock(100000000L, num2);
				
				Map<String, Long> successRecordMap=new HashMap<>();
				successRecordMap.put("seckillnum", num2);
				successRecordMap.put("stocknum",decreaseStock.get("stock"));
				successRecord.add(successRecordMap);
				return new ResultMessage(ResultMessage.Success, "秒杀成功");
			} catch (Exception e) {
				return new ResultMessage(ResultMessage.Fail,e.getMessage());
			}
		}
	}
	@GetMapping
	@RequestMapping("seckill/result")
	public  ResultMessage seckillResult(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		Long total=0L;
		List<Map<String,Long>> list=new ArrayList<>();
		for (Map<String,Long> ma : successRecord) {
			list.add(ma);
			total=total+ma.get("seckillnum");
		}
		return new ResultMessage(ResultMessage.Success, mapper.writeValueAsString(list)+" 总共秒杀数量："+total);
	}
}
