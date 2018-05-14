package com.imooc.security.app;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
public class ImoocAuthorizationServer extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
    private DataSource dataSource;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }
	@Bean // 声明TokenStore实现
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
	
    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetails());
        tokenServices.setAccessTokenValiditySeconds(60); // token有效期自定义设置，默认1分钟
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);//默认30天，这里修改
        return tokenServices;
    }

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);
        endpoints.tokenStore(tokenStore()).tokenServices(defaultTokenServices());
	}
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetails());
	}
	
}
