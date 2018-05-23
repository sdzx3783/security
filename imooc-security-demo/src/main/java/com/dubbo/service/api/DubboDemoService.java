package com.dubbo.service.api;

import java.util.List;

import com.dubbo.model.DubboDemoModel;

public interface  DubboDemoService {
	public List <DubboDemoModel> getData(String id);
}
