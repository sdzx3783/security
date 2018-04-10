package com.imooc.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.codehaus.jackson.map.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import com.imooc.dto.SimpleModel;
import com.imooc.dto.SimpleSubModel;

@RestController
@RequestMapping("/simple")
public class SimpleController {
	@RequestMapping("/save.ht")
	public Map<String, Object> save(@Valid SimpleModel simpleModel,BindingResult errors) {
		Map ret = new HashMap<>();
		ret.put("status", "success");
		ret.put("msg", "");
		
		if (errors.hasErrors()) {
			List<String> err=new ArrayList<>();
			List<ObjectError> allErrors = errors.getAllErrors();
			for (ObjectError objectError : allErrors) {
				err.add(objectError.getDefaultMessage());
				System.out.println(objectError.getObjectName()+" "+objectError.getCode()+" "+objectError.getDefaultMessage());
			}
			ret.put("status", "error");
			ret.put("msg", err);
			return ret;
		}
		System.out.println("main name:"+simpleModel.getName()+" date:"+simpleModel.getApplicateTime());
		for (SimpleSubModel sub : simpleModel.getSimpleSubList()) {
			System.out.println("sub name:"+sub.getSusbName()+" date:"+sub.getSubApplicateTime());
		}
		return ret;
	}

	@RequestMapping("/edit.ht")
	public ModelAndView edit() {
		ModelAndView mv = new ModelAndView("simpleModelEdit");
		return mv;
	}
	/**
	 * 类型转换
	 * @param request
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Integer.class, null, true));
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new SmartDateEditor());
	}
	
	/**@ModelAttribute 第一种用法 （该方法会在控制器所有方法调用前执行）
	 * 那么此save方法中参数simpleModel就会是test方法返回的 simpleModel.(前提，@ModelAttribute 的model名称要一致)
	 * 如果save.ht请求中包含simpleModel的同名属性参数时，那么save方法中的simpleModel对应的属性会被再次赋值覆盖。
	 * （如 /save?name=sun）,save方法的到的simpleModel name属性为sun applicateTime还是test()方法返回时的值。
	 * 这中特性（原有的值不变，表单里提交的值才改变）可用做，字段多的表单保存操作！ 参见oa例子。
	 * @return
	 */
	@ModelAttribute("simpleModel")
	public SimpleModel test() {
		SimpleModel simple=new SimpleModel();
		simple.setName("test");
		simple.setApplicateTime(new Date());
		return simple;
	}
	
	
}
