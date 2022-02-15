package com.dev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dev.service.impl.AdminLoginServiceImpl;
import com.dev.service.impl.UserLoginServiceImpl;

@Configuration
@EnableWebSecurity
@Order(-1)
public class Security extends WebSecurityConfigurerAdapter{
	@Autowired
	AdminLoginServiceImpl adminLoginServiceImpl;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(adminLoginServiceImpl).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stubvs
//		http.authorizeRequests().antMatchers("/register", "/login", "/logout").permitAll();
//// 
////        // Trang chỉ dành cho ADMIN
//////        http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");
//		
////        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
//// 
////        // Cấu hình cho Login Form.
////        http.formLogin().loginPage("/admin/login").defaultSuccessUrl("/admin/dashboard").failureUrl("/admin/login?e=error").permitAll().and().logout().logoutSuccessUrl("/admin/login?logout=true")
////       .invalidateHttpSession(true).permitAll().
////		and().exceptionHandling().accessDeniedPage("/admin/login?e=deny");    
//        http
//        .formLogin().loginPage("/admin/login")
//			.defaultSuccessUrl("/admin/dashboard", true)
//			.failureUrl("/admin/login?e=error")
//		.permitAll()
//		.and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/admin/login?logout=true")
//		.and().exceptionHandling().accessDeniedPage("/admin/login?e=deny");
//	http.csrf().disable();
		http
        .csrf().disable()
        .requestMatchers().antMatchers("/admin/**")
        .and()
        .authorizeRequests().anyRequest().access("hasRole('ROLE_ADMIN')")
        .and().formLogin()
        .loginPage("/admin/login").permitAll()
        .defaultSuccessUrl("/admin/dashboard").failureUrl("/admin/login?e=error")
        .and().logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout")).logoutSuccessUrl("/admin/login?logout=true").and()
        .exceptionHandling().accessDeniedPage("/admin/login?e=deny");
		
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() { 
	    return new BCryptPasswordEncoder(16); 
	}
	
	public static void main(String[] args) {
		String rawPass = "Kocopass123";
		String encode = new BCryptPasswordEncoder().encode(rawPass);
		System.out.println(encode);
		//
		boolean check = new BCryptPasswordEncoder().matches(rawPass, encode);
	}
}
