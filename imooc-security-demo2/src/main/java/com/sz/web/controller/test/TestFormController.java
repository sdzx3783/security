package com.sz.web.controller.test;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sz.model.Test;
import com.sz.service.test.TestService;
import com.sz.web.ResultMessage;
import com.sz.web.controller.BaseFormController;
@Controller
@RequestMapping("/platform/sz/")
public class TestFormController extends BaseFormController {
	@Autowired
	private TestService testService;

	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response,@Valid Test test, BindingResult bindResult) throws Exception {
		logger.info("保存Test...");
		PrintWriter writer = response.getWriter();
		ResultMessage rs=null;
		if (bindResult.hasErrors()) {
			List<ObjectError> allErrors = bindResult.getAllErrors();
			String defaultMessage = allErrors.get(0).getDefaultMessage();
			rs=new ResultMessage(ResultMessage.Fail, defaultMessage);
			writer.print(rs);
			writer.flush();
			return ;
		}
		if(test.getId()==null) {
			testService.add(test);
		}else {
			testService.update(test);
		}
		//testService.testTransaction(test);
		rs=new ResultMessage(ResultMessage.Success, test.getId()==null?"保存成功":"修改成功");
		writer.print(rs);
		writer.flush();
		return ;
	}
	
	@ModelAttribute
	protected Test getFormObject(@RequestParam("id") Long id, 	Model model) throws Exception{
		logger.debug("enter Test getFormObject here....");
		Test test = null;
		if (id != null){
			test = testService.getById(id);
		} 
		else{
			test = new Test();
		}
		return test;
	}
}
