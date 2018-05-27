package com.sz.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.SessionEventHttpSessionListenerAdapter;

import com.sz.web.listener.UserSessionListener;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60*30)
public class SessionConfig {
	@Bean
	public SessionEventHttpSessionListenerAdapter sessionEventHttpSessionListenerAdapter() {
		List<HttpSessionListener> list=new ArrayList<HttpSessionListener>();
		list.add(new UserSessionListener());
		return new SessionEventHttpSessionListenerAdapter(list);
	}
}