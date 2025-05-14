package com.test.shoebox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	//암호 인코더
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterchain(HttpSecurity http) throws Exception {


		http.authorizeHttpRequests(
			auth -> auth.requestMatchers("/**").permitAll()
						//.requestMatchers("/register", "/registerok").permitAll()
						//.requestMatchers("/member").hasAnyRole("MEMBER", "ADMIN")
						//.requestMatchers("/admin").hasRole("ADMIN")
						.anyRequest().authenticated()
		);
		
		http.csrf(
			auth -> auth.disable()
		);
		
		//커스텀 로그인 페이지
		http.formLogin(
				auth -> auth.loginPage("/login")
							.loginProcessingUrl("/loginok")
							//.defaultSuccessUrl("/")
		);
		
		//로그아웃
		http.logout(
			auth -> auth.logoutUrl("/logout")
						.logoutSuccessUrl("/")
		);
		
		return http.build();
	}

}
















