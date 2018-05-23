package com.dubbo.model;

import java.io.Serializable;
import java.util.Date;

public class DubboDemoModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 605397883842805285L;
	private String name;
	private Long id;
	private Date ctime;
	
	public DubboDemoModel(String name, Long id, Date ctime) {
		super();
		this.name = name;
		this.id = id;
		this.ctime = ctime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	
}
