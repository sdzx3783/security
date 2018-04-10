package com.imooc.dto;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.imooc.validator.SimpleDateConstraint;

public class SimpleModel {
	private Long id;
	@NotEmpty(message="姓名不能为空")
	private String name;
	@SimpleDateConstraint(canBeNull=false,minDate="2017-01-01",maxDate="2017-02-01")
	private Date applicateTime;
	@NotEmpty(message="字表不能为空")
	private List<SimpleSubModel> simpleSubList;
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
	public Date getApplicateTime() {
		return applicateTime;
	}
	public void setApplicateTime(Date applicateTime) {
		this.applicateTime = applicateTime;
	}
	public List<SimpleSubModel> getSimpleSubList() {
		return simpleSubList;
	}
	public void setSimpleSubList(List<SimpleSubModel> simpleSubList) {
		this.simpleSubList = simpleSubList;
	}
	
}
