package com.sz.web.controller.socket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sz.web.controller.BaseController;
import com.sz.web.listener.OnlineUser;
import com.sz.web.listener.UserSessionListener;
@Controller
@RequestMapping("/system/socket")
public class SocketController extends BaseController {
	@RequestMapping("client")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv=new ModelAndView("socket/socketClient");
		//int onlineCount = WebSocket.getOnlineCount();
		Map<Long, OnlineUser> onLineUsers = UserSessionListener.getOnLineUsers();
		
		return mv.addObject("onLineUsers", onLineUsers);
	}
}
