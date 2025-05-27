package com.test.shoebox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.test.shoebox.service.main.CustomOAuth2UserService;
import com.test.shoebox.service.main.OAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;
	
	@Bean
	SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
		
		//CRSF > 비활성화
		http.csrf(auth -> auth.disable());
		
		//Form 로그인 방식(X) > 소셜 인증
		http.formLogin(auth -> auth.disable());
		
		//기본 인증 > 사용 안함
		http.httpBasic(auth -> auth.disable());
		
		//허가 URI
		http.authorizeHttpRequests(auth -> auth
			.requestMatchers("/**").permitAll()
			//.requestMatchers("/css").permitAll()
			.anyRequest().authenticated()
		);
		
		http.oauth2Login(auth -> auth
			.loginPage("/main/login")
			.userInfoEndpoint(config 
						-> config.userService(customOAuth2UserService))
			//.defaultSuccessUrl("/main/") // 로그인 성공 후 항상 이동
		    .successHandler(oAuth2SuccessHandler) // 동적 리다이렉션 하고 싶으면 이 방식
		);

		http.logout(logout -> logout
            .logoutUrl("/main/logout") // 기본값은 /logout 이지만 명시 가능
            .logoutSuccessUrl("/main/") // 로그아웃 후 이동할 페이지
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        );

		return http.build();
	}
}
