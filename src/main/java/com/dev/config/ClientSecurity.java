package com.dev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dev.service.impl.UserLoginServiceImpl;

@EnableWebSecurity
@Configuration
@Order(1)
public class ClientSecurity extends WebSecurityConfigurerAdapter{
	@Autowired
	UserDetailsService userLoginServiceImpl;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userLoginServiceImpl).passwordEncoder(passwordEncoder2());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stubvs
		http
        .csrf().disable();
		
		http.authorizeRequests().antMatchers("/signup", "/logout", "/member/login").permitAll();
   
        // Trang chỉ dành cho MEMBER
		http.authorizeRequests().antMatchers("/member/**").access("hasRole('ROLE_USER')");
		 
        // Khi người dùng đã login, với vai trò XX.
        // Nhưng truy cập vào trang yêu cầu vai trò YY,
        // Ngoại lệ AccessDeniedException sẽ ném ra.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
 
        // Cấu hình cho Login Form.
        http.formLogin().loginPage("/member/login").defaultSuccessUrl("/product/search", true).failureUrl("/member/login?e=error").permitAll().and().logout().logoutSuccessUrl("/member/login?logout=true")

		.and().exceptionHandling().accessDeniedPage("/member/login?e=deny");
//		http.requestMatcher(new AntPathRequestMatcher("/member/**"))
//        .csrf().disable()
//        .authorizeRequests()
//        .antMatchers("/member/**").access("hasRole('ROLE_USER')").and().formLogin()
//        .loginPage("/member/login").permitAll()
//        .defaultSuccessUrl("/product/search")
//        .failureUrl("/member/login?e=error").and().logout()
//        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/member/login?logout=true").and()
//        .exceptionHandling().accessDeniedPage("/member/login?e=deny");
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder2() { 
	    return new BCryptPasswordEncoder(16); 
	}
}
