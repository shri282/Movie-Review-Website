package com.shri.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.shri.Services.Userdetialservice;

@Configuration
@EnableWebSecurity
public class Securityconfig {

	
	@Bean
	protected UserDetailsService userdetialservice(PasswordEncoder encoder) {
		
		return new Userdetialservice();

	}
	
	
	
	@Bean
	protected PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	protected SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception {
		
		 http
		     
		     .authorizeHttpRequests(auth -> {
		    	 auth.requestMatchers("/login","/","/register","/register-user","/css/**", "/script/**","/images/**").permitAll()
		    	 .requestMatchers("watchlater/addwl").authenticated()
		    	 .anyRequest().authenticated();
		     }).formLogin(login -> login
                     .loginPage("/login").defaultSuccessUrl("/").permitAll())
             .logout(logout -> logout.invalidateHttpSession(true)
                     .clearAuthentication(true)
                     .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessHandler(logoutSuccessHandler())
                     .permitAll()).csrf(csrf -> csrf.disable());
				
		return http.build();
	}
	
	
	@Bean
	protected LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }
	
	
	
}
