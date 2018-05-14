package com.imooc.security.model;

public class SimpleResponse {
	private Object content;

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	public SimpleResponse(Object obj) {
		this.content = obj;
	}
	
}
