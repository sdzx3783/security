package com.sz.web.controller.test;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sz.model.Test;
import com.sz.service.test.TestService;
import com.sz.web.ResultMessage;
import com.sz.web.controller.BaseFormController;
import com.sz.web.query.QueryFilter;

import util.RequestUtil;
@Controller
@RequestMapping("/platform/sz/")
public class TestController extends BaseFormController{
	private static Logger logger = LoggerFactory.getLogger(TestController.class);
	@Autowired
	private TestService testService;
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request);
		int pageSize = RequestUtil.getInt(request, "pageSize", 10);
		queryFilter.getPageBean().setPagesize(pageSize);
		List<Test> all = testService.getAll(queryFilter);
		ModelAndView mv=new ModelAndView("test/testList");
		return  mv.addObject("list", all);
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		Test byId = testService.getById(id);
		String returnUrl=RequestUtil.getPrePage(request);
		ModelAndView mv=new ModelAndView("test/testEdit");
		
		return mv.addObject("returnUrl", returnUrl)
				.addObject("test", byId);
	}
	@RequestMapping("del")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long[] ids=null;
		ResultMessage rs = null;
		HttpSession session = request.getSession();
		String prePage = RequestUtil.getPrePage(request);
		try {
			ids=RequestUtil.getLongAry(request,"id");
			testService.delByIds(ids);
			
		} catch (Exception e) {
			logger.error("删除Test异常:",e);
			rs=new ResultMessage(ResultMessage.Fail, "删除Test失败");
			session.setAttribute("message", rs);
			response.sendRedirect(prePage);
			return ;
		}
		
		rs=new ResultMessage(ResultMessage.Success, "删除Test成功");
		session.setAttribute("message", rs);
		response.sendRedirect(prePage);
	}
}
