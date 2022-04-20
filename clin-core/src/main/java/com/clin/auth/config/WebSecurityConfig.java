package com.clin.auth.config;

import com.clin.auth.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Web安全配置类
 *
 *
 */
@Configuration
@Order(11)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthServiceImpl authServiceImpl;

	/**
	 * 为特定的Http请求配置基于Web的安全约束
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
				.antMatchers("/**/oauth/token", "/**/actuator/**", "/**/sysuserdetail/registerAccount", "/**/sysuserdetail/retrievePassword",
						"/**/sysuserdetail/compareCaptcha", "/**/captcha/generateImageCaptcha", "/**/captcha/getSmsCaptcha", "/**/swagger-ui/**",
						"/**/swagger-ui.html/**", "/**/webjars/springfox-swagger-ui/**", "/**/swagger-resources/**", "/**/v2/api-docs/**",
						"/**/static/upload/**","/**/pro/**","/**/mp/**","/**/file/**")
				.permitAll().anyRequest().authenticated().and().csrf().disable();
	}

	/**
	 * 配置认证信息
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(authServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
	}

	/**
	 * 实例化AuthenticationManager对象，以处理认证请求
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
