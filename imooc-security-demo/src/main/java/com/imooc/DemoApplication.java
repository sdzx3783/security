package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

@SpringBootApplication
@ComponentScan(basePackages= {"com.imooc","com.sz","com.dubbo"})
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableDubboConfiguration
@RestController
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@GetMapping("/hello")
	public String hello(){
		return "hello";
	}
	
}
