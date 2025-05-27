package com.test.shoebox.service.main;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.test.shoebox.entity.Members;
import com.test.shoebox.repository.MembersRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	private final MembersRepository membersRepository;
	private final HttpSession session;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {

		//인증받은 사람의 이메일?
		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
		
		Optional<Members> members = membersRepository.findByEmail(oAuth2User.getEmail());
		
		if (members.isPresent()) {
			//재방문 > 홈으로 이동
	        oAuth2User.getMembersDTO().setMembersId(members.get().getMembersId());
	        oAuth2User.getMembersDTO().setRole("ROLE_MEMBER");
	        
			response.sendRedirect(request.getContextPath() + "/main/");
		} else {
			//첫방문 > 회원가입으로 이동
			session.setAttribute("members", oAuth2User);
			response.sendRedirect(request.getContextPath() + "/main/register");
		}

	}
	
}
