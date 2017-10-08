package com.jhon.blog.bs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>功能描述</br> Spring Security 配置类</p>
 * @className  WebSecurityConfig
 * @author  jiangy19
 * @date  2017年10月1日 下午9:49:27
 * @version  v1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法安全设置
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String KEY = "jhonrain.org";
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		/** 使用 BCrypt 加密 **/
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		/** 设置密码加密方式 **/
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/plugins/**","/css/**", "/js/**", "/fonts/**", "/index").permitAll() // 都可以访问
			.antMatchers("/h2-console/**").permitAll() // 都可以访问
			.antMatchers("/admins/**").hasRole("ADMIN")//需要对应的角色才可以访问 
			.and()
			.formLogin()   //基于 Form 表单登录验证
			.loginPage("/login")
			.failureUrl("/login-error") // 自定义登录界面
			.and().rememberMe().key(KEY) // 启用 remember me
			.and().exceptionHandling().accessDeniedPage("/403");  // 处理异常，拒绝访问就重定向到 403 页面
		http.csrf().ignoringAntMatchers("/h2-console/**"); // 禁用 H2 控制台的 CSRF 防护
		http.headers().frameOptions().sameOrigin(); // 允许来自同一来源的H2 控制台的请求
	}
	
	
	@Autowired
	public void configuraGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}
	
	/*@Override
	public void configure(WebSecurity web) throws Exception {
		//解决静态资源被拦截的问题
		web.ignoring().antMatchers("/plugins/**");
	}*/
}
