package com.imooc.security.core.validate.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode extends ValidateCode{
	private BufferedImage image;
	
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
		super();
		this.image = image;
		this.code = code;
		this.expireTime = expireTime;
	}
	public ImageCode(BufferedImage image, String code, int expireIn) {
		super();
		this.image = image;
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
