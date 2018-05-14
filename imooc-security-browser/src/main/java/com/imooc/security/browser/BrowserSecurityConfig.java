package com.imooc.security.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeFilter;
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	@Autowired
	private SpringSocialConfigurer immocSpringSocialConfigurer;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		ValidateCodeFilter codeFilter=new ValidateCodeFilter();
		codeFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
		codeFilter.setSecurityProperties(securityProperties);
		codeFilter.afterPropertiesSet();
		http.apply(immocSpringSocialConfigurer); 
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http.addFilterBefore(codeFilter, UsernamePasswordAuthenticationFilter.class)
		.formLogin()
		//.loginPage("/imooc-signIn.jsp")
		.loginPage("/authentication/require")//跟LoginUrlAuthenticationEntryPoint有关，Oa项目配置了一个MultipleAuthenticationLoginEntry
		.loginProcessingUrl("/authentication/form")
		.successHandler(imoocAuthenticationSuccessHandler)
		.failureHandler(imoocAuthenticationFailureHandler)
		//http.httpBasic()
			.and()
			.authorizeRequests();
		authorizeRequests.antMatchers("/authentication/require","/code/image",
					securityProperties.getBrowser().getLoginPage(),"/platform/**","/styles/**","/js/**").permitAll();
		authorizeRequests.anyRequest().access("@rbacService.hasPermission(request, authentication)")
		.and()
		.csrf().disable();
	}
}
