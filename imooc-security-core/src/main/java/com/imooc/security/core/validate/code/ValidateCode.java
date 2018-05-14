package com.imooc.security.core.validate.code;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ValidateCode implements Serializable{
	private static final long serialVersionUID = -3269694369775334180L;
	protected String code;
	protected LocalDateTime expireTime;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public LocalDateTime getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}
	public boolean isExpire(){
		return LocalDateTime.now().isAfter(expireTime);
	}
	public ValidateCode(String code, LocalDateTime expireTime) {
		super();
		this.code = code;
		this.expireTime = expireTime;
	}
	public ValidateCode() {
		super();
	}
	
}
