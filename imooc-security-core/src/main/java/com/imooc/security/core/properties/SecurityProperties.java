package com.imooc.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="imooc.security")
public class SecurityProperties {
	//attention : 此处的属性需要new 对象() 
	private BrowserProperties browser=new BrowserProperties();
	private ValidateProperties code=new ValidateProperties();
	
	public BrowserProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}

	public ValidateProperties getCode() {
		return code;
	}

	public void setCode(ValidateProperties code) {
		this.code = code;
	}
	
}
