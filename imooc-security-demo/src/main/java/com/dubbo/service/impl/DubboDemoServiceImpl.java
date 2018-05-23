package com.dubbo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.model.DubboDemoModel;
import com.dubbo.service.api.DubboDemoService;
@Service(interfaceClass=DubboDemoService.class,version="1.0.0")
@Component
public class DubboDemoServiceImpl implements DubboDemoService {

	@Override
	public List<DubboDemoModel> getData(String id) {
		
		List<DubboDemoModel> list=new ArrayList<DubboDemoModel>();
		DubboDemoModel d1=new DubboDemoModel("sun", 1000000000000L, new Date());
		DubboDemoModel d2=new DubboDemoModel("dong", 2000000000000L, new Date());
		DubboDemoModel d3=new DubboDemoModel("xiu", 3000000000000L, new Date());
		list.add(d1);
		list.add(d2);
		list.add(d3);
//		try {
//			Thread.sleep(60*1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return list;
	}

}
