package com.imooc.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.social.security.SpringSocialConfigurer;

import com.imooc.security.app.authentication.ImoocDeniedHandler;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeFilter;
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ImoocResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	@Autowired
	private SpringSocialConfigurer immocSpringSocialConfigurer;
	@Autowired
    private DefaultTokenServices defaultTokenServices;
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.setSharedObject(SecurityContextRepository.class,new HttpSessionSecurityContextRepository());
		/**http.setSharedObject(SecurityContextRepository.class,new HttpSessionSecurityContextRepository());
		 * //想要使用浏览器还能实现基于session的登陆就需要配置这个  如果不配制，那么由于
		 * 配置了资源服务器SecurityContextRepository的默认配置实现类将是NullSecurityContextRepository，而基于session的会话保持需要HttpSessionSecurityContextRepository 
		 * //由于配置了认证/资源服务器，那么OAuth2AuthenticationProcessingFilter将起作用，默认会把SecurityHolder里的SecurityContext清空!
		 */
		http.setSharedObject(RequestCache.class,new HttpSessionRequestCache());
		/**http.setSharedObject(RequestCache.class,new HttpSessionRequestCache());
		 * //想要使用浏览器还能实现基于session的登陆就需要配置这个  如果不配制，那么由于
		 * 配置了资源服务器RequestCache的默认配置实现类将是NullRequestCache，而基于session的会话保持需要HttpSessionRequestCache 
		 */
		ValidateCodeFilter codeFilter=new ValidateCodeFilter();
		codeFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
		codeFilter.setSecurityProperties(securityProperties);
		codeFilter.afterPropertiesSet();
		http.apply(immocSpringSocialConfigurer); 
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http.addFilterBefore(codeFilter, UsernamePasswordAuthenticationFilter.class)
		.formLogin()
		//.loginPage("/imooc-signIn.jsp")
		//.loginPage("/authentication/require")//跟LoginUrlAuthenticationEntryPoint有关，Oa项目配置了一个MultipleAuthenticationLoginEntry
		.loginProcessingUrl("/authentication/form")
		.successHandler(imoocAuthenticationSuccessHandler)
		.failureHandler(imoocAuthenticationFailureHandler)
		//http.httpBasic()
			.and()
			.authorizeRequests();
		authorizeRequests.antMatchers("/authentication/require","/code/image","/oauth/**",
					securityProperties.getBrowser().getLoginPage(),"/platform/**","/styles/**","/js/**").permitAll();
			
		authorizeRequests
		.antMatchers(HttpMethod.GET, 
				"/**/*.html",
				"/admin/me",
				"/resource").authenticated();
		authorizeRequests.anyRequest().access("@rbacService.hasPermission(request, authentication)")
			.and()
			.csrf().disable();
		http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/authentication/require"));
		http.exceptionHandling().accessDeniedHandler(new ImoocDeniedHandler());
	}
	
	@Autowired
	private DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler;
	@Autowired
	private TokenStore tokenStore;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		super.configure(resources);
		resources.tokenServices(defaultTokenServices).tokenStore(tokenStore);
		//该ImoocOAuth2AuthenticationEntryPoint不能拦截.html后缀请求的未认证处理(修改 该配置移至上面配置)
		//resources.authenticationEntryPoint(new ImoocOAuth2AuthenticationEntryPoint());
		//OAuth2AuthenticationProcessingFilter会根据stateless配置决定是否每一次请求都先执行SecurityContextHolder.clearContext();
		resources.stateless(false);
		//由于配置了认证/资源服务器 expressionHandler变成了OAuth2WebSecurityExpressionHandler，该handler不支持@rbacService.hasPermission(request, authentication)表达式
		resources.expressionHandler(defaultWebSecurityExpressionHandler);
		
	}
}
