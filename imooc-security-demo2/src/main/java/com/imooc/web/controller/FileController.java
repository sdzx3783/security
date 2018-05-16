package com.imooc.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.imooc.dto.FileInfo;

@RestController
@RequestMapping("file")
public class FileController {
	@PostMapping
	public FileInfo upload(MultipartFile file) throws IllegalStateException, IOException{
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());
		String folder="E:/imooc/imooc-security-demo/src/main/java/com/imooc/web/controller";
		File localfile=new File(folder,new Date().getTime()+".txt");
		file.transferTo(localfile);
		
		return new FileInfo(localfile.getAbsolutePath());
	}
	
	@GetMapping("/{id}")
	public void download(@PathVariable String id,HttpServletRequest request,HttpServletResponse response){
		String folder="E:/imooc/imooc-security-demo/src/main/java/com/imooc/web/controller";
		try {
			InputStream inputStream=new FileInputStream(new File(folder,id+".text"));
			ServletOutputStream outputStream = response.getOutputStream();
			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition", "attachment;filename=test.text");
			IOUtils.copy(inputStream, outputStream);
			outputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}
}
