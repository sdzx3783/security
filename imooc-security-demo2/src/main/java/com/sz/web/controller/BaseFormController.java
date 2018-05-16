package com.sz.web.controller;

import java.text.NumberFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.imooc.web.controller.SmartDateEditor;



public class BaseFormController extends GenericController {
	public Logger logger = LoggerFactory.getLogger(BaseFormController.class);

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		this.logger.debug("init binder ....");
		binder.registerCustomEditor(Integer.class, (String) null,
				new CustomNumberEditor(Integer.class, (NumberFormat) null, true));
		binder.registerCustomEditor(Long.class, (String) null,
				new CustomNumberEditor(Long.class, (NumberFormat) null, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(Date.class, new SmartDateEditor());
	}

	
}