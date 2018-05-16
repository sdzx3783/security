package com.imooc.dto;

public class FileInfo {
	private String path;
	
	private String name;

	public FileInfo(String path){
		this.path=path;
	}
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
