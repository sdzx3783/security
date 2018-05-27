package com.sz.web.controller;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.imooc.web.controller.SmartDateEditor;
import com.sz.core.page.PageList;
import com.sz.web.ResultMessage;



public class BaseController extends GenericController {
	public static final String Message = "message";
//	@Resource
//	protected ConfigurableBeanValidator confValidator;

	public void addMessage(ResultMessage message, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("message", message);
	}
	@Deprecated
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		this.logger.debug("init binder ....");
		binder.registerCustomEditor(Integer.class, (String) null,
				new CustomNumberEditor(Integer.class, (NumberFormat) null, true));
		binder.registerCustomEditor(Long.class, (String) null,
				new CustomNumberEditor(Long.class, (NumberFormat) null, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new SmartDateEditor());
	}

	
	protected Map<String, Object> getMapByPageList(PageList pageList) {
		HashMap map = new HashMap();
		map.put("rows", pageList);
		map.put("total", Integer.valueOf(pageList.getTotalCount()));
		return map;
	}

	protected Map<String, Object> getMapByPageListJq(PageList pageList) {
		HashMap map = new HashMap();
		map.put("rows", pageList);
		map.put("records", Integer.valueOf(pageList.getTotalCount()));
		map.put("page", Integer.valueOf(pageList.getPageBean().getCurrentPage()));
		map.put("total", Integer.valueOf(pageList.getTotalPage()));
		return map;
	}
}