package com.test.shoebox.service.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.test.shoebox.dto.MembersDTO;

//CustomerUserDetails > 인증 정보 객체(세션)

public class CustomOAuth2User implements OAuth2User {

	private final MembersDTO membersDTO;
	
	public CustomOAuth2User(MembersDTO membersDTO) {
		this.membersDTO = membersDTO;
	}
	
	public Map<String, Object> getAttributes() {
		//사용 안함
		return null;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> list = new ArrayList<>();
		
		GrantedAuthority auth = () -> membersDTO.getRole();
		
		list.add(auth);
		
		return list;
	}

	@Override
	public String getName() {
		
		return membersDTO.getName();
	}

	public MembersDTO getMembersDTO() {
		
		return membersDTO;
	}
	
	public String getEmail() {
		
		return membersDTO.getEmail();
	}
	
	public Long getMembersId() {
		
		return membersDTO.getMembersId();
	}
	
}











