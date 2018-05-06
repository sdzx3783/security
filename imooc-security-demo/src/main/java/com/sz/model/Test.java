package com.sz.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.imooc.validator.SimpleDateConstraint;

public class Test {
	private Long id;
	@NotEmpty(message="姓名不能为空")
	private String name;
	@NotNull(message="年龄不能为空")
	private Integer age;
	@SimpleDateConstraint
	private Date birthday;
	@SimpleDateConstraint
	private Date ctime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	
}
