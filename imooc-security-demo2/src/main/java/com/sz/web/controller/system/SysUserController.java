package com.sz.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.imooc.security.model.SysUser;
import com.sz.web.controller.BaseController;
import com.sz.web.query.QueryFilter;
import com.sz.web.service.system.SysUserService;
@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
	@Autowired
	private SysUserService sysUserService;
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv=new ModelAndView("system/list");
		QueryFilter queryFilter=new QueryFilter(request);
		List<SysUser> all = sysUserService.getAll(queryFilter);
		return mv.addObject("list", all);
	}
}
