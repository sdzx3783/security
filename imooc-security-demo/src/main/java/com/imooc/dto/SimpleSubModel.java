package com.imooc.dto;

import java.util.Date;

import com.imooc.validator.MyConstraint;
import com.imooc.validator.SimpleDateConstraint;

public class SimpleSubModel {
	private Long refId;
	private Long id;
	private String susbName;
	@SimpleDateConstraint(canBeNull=false,minDate="2017-01-01",maxDate="2017-02-01")
	private Date subApplicateTime;
	public Long getRefId() {
		return refId;
	}
	public void setRefId(Long refId) {
		this.refId = refId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSusbName() {
		return susbName;
	}
	public void setSusbName(String susbName) {
		this.susbName = susbName;
	}
	public Date getSubApplicateTime() {
		return subApplicateTime;
	}
	public void setSubApplicateTime(Date subApplicateTime) {
		this.subApplicateTime = subApplicateTime;
	}
	
	
}
