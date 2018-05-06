package com.imooc.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import com.imooc.dto.UserQueryCondition;
import com.imooc.exception.UserNotExistException;
@RestController
@RequestMapping(value="/user")
public class UserController {
	
	/*@RequestMapping(value="/user",method=RequestMethod.GET)
	public List<User> query(@RequestParam(required=true,defaultValue="tom") String username){
		List<User> list=new ArrayList<>();
		list.add(new User());
		list.add(new User());
		list.add(new User());
		return list;
	}*/ 
	@PostMapping
	public User create(@Valid @RequestBody User user/*,BindingResult errors*/){
		/*if(errors.hasErrors()){
			List<ObjectError> allErrors = errors.getAllErrors();
			for (ObjectError objectError : allErrors) {
				System.out.println(objectError.getDefaultMessage());
			}
		}*/
		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getBirthday());
		user.setId("1");
		return user;
	}
	
	@GetMapping
	@JsonView(User.UserSimpleView.class)
	public List<User> query(UserQueryCondition condition,@PageableDefault(page=1,size=10,sort="username,desc")Pageable pageable){
		
		System.out.println(ReflectionToStringBuilder.toString(condition));
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getSort());
		List<User> list=new ArrayList<>();
		list.add(new User());
		list.add(new User());
		list.add(new User());
		return list;
	}
	//@RequestMapping(value="/user/{id:\\d+}",method=RequestMethod.GET)
	@GetMapping(value="/{id:\\d+}")
	@JsonView(User.UserDetailView.class)
	public User getInfo(@PathVariable(name="id") String idxxx){
		//throw new UserNotExistException("111");
		//throw new RuntimeException("111");
		System.out.println("进入info服务");
		User user=new User();
		user.setUsername("tom");
		return null;
	}
	
	@PutMapping(value="/{id:\\d+}")
	@JsonView(User.UserDetailView.class)
	public User update(@Valid @RequestBody User user,BindingResult errors){
		if(errors.hasErrors()){
			Iterator<ObjectError> iterator = errors.getAllErrors().iterator();
			while(iterator.hasNext()){
				System.out.println(iterator.next().getDefaultMessage());
			}
		}
		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getBirthday());
		user.setId("1");
		return user;
	}
	@DeleteMapping(value="/{id:\\d+}")
	public void delete(@PathVariable(name="id") String idxxx){
		System.out.println(idxxx);
	}
	
	
}