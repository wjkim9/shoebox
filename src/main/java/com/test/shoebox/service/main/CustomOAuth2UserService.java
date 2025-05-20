package com.test.shoebox.service.main;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.test.shoebox.dto.MembersDTO;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {


	//리소스 서버로부터 받아오는 개인 정보 > 네이버 회원 정보
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		System.out.println("받아온 개인 정보: " + oAuth2User);
		
		//네이버 or 구글 확인
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		
		OAuth2Response oAuth2Response = null; 
		
		if (registrationId.equals("naver")) {
			
			oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
			
		} else if (registrationId.equals("google")) {
			
			oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
			
		}
		
		//받아온 네이버 정보 > 스프링 시큐리티
		//UserDTO userDTO = new UserDTO();
		MembersDTO membersDTO = new MembersDTO();
		//네이버: hong > naver hong
		//구글: hong > google hong
		
		membersDTO.setAccountName(oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId());
		membersDTO.setName(oAuth2Response.getName());
		membersDTO.setRole("ROLE_MEMBER");
		membersDTO.setEmail(oAuth2Response.getEmail());
		
		return new CustomOAuth2User(membersDTO); 
	}
	
}

