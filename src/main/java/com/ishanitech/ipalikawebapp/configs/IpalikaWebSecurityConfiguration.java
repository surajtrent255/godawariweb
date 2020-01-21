package com.ishanitech.ipalikawebapp.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class IpalikaWebSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	/*
	 * @Autowired private CustomAuthenticationProvider authenticationProvider;
	 * 
	 * @Autowired private CustomAuthenticationSuccessHandler
	 * authenticationSuccessHandler;
	 */	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.authenticationProvider(authenticationProvider);
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/fonts/**", "/images/**", "/includes/**", "/js/**", "/vendor/**");
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			//.antMatchers("/css/**", "/fonts/**", "/images/**", "/includes/**", "/js/**", "/vendor/**").permitAll()
			//.antMatchers("/", "/error", "/about", "/contact", "/login", "/category/**", "/cart/**", "/products/cart").permitAll()
			.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
			.antMatchers("/customer/**").hasAnyAuthority("ROLE_CUSTOMER")
			.and()
			.formLogin()
			.loginPage("/login")
			.usernameParameter("email")
			.passwordParameter("password")
			//.successHandler(authenticationSuccessHandler)
			.failureUrl("/login?error=true")
			.and()
			.logout().permitAll()
			.and()
			.csrf().disable();
	}
	
}
