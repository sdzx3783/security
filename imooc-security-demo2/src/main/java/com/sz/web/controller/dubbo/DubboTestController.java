package com.sz.web.controller.dubbo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.model.DubboDemoModel;
import com.dubbo.service.api.DubboDemoService;
import com.sz.web.controller.BaseController;
@Controller
@RequestMapping("/platform/dubbo/")
public class DubboTestController extends BaseController {
	@Reference(version="1.0.0",timeout=60*1000)  
    private DubboDemoService dubboDemoService;
	@RequestMapping("test/{id}")
	public @ResponseBody List<DubboDemoModel> testDubboService(@PathVariable String id,HttpServletRequest request,HttpServletResponse response) {
		List<DubboDemoModel> data = dubboDemoService.getData(id);
		return data;
	}
}
