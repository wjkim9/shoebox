package com.test.shoebox.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.shoebox.dto.MemberAddressDTO;
import com.test.shoebox.dto.MembersDTO;
import com.test.shoebox.entity.Members;
import com.test.shoebox.repository.MemberAddressRepository;
import com.test.shoebox.repository.MembersRepository;
import com.test.shoebox.service.main.CustomOAuth2User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class LoginController {

	private final MembersRepository membersRepository;
	private final MemberAddressRepository memberAddressRepository;
	
	@GetMapping("/login")
	public String login(Model model) {

		return "main/login";
	}

	@GetMapping("/register")
	public String register(HttpSession session, Model model) {

		CustomOAuth2User oauthUser = (CustomOAuth2User) session.getAttribute("members");
		
	    if (oauthUser == null) {
	        // 예외 처리 또는 로그인 페이지로 리다이렉트
	        return "redirect:/main/login";
	    }

	    // 필요한 정보만 모델로 전달 (ex. email, name 등)
	    model.addAttribute("email", oauthUser.getEmail());
	    model.addAttribute("name", oauthUser.getName());
		
		return "main/register";
	}
	
	@PostMapping("/registerok")
	public String registerok(Model model, MembersDTO mdto, MemberAddressDTO madto) {
		
		//System.out.println("유저~~~~" + mdto.toString());
		//System.out.println(mdto.getName());
		//System.out.println("유저 주소~~~~" + madto.toString());
		
		mdto.setAccountName("-");
		mdto.setPassword("OAUTH2_USER");
		mdto.setPoint(5000);
		
		membersRepository.save(mdto.toEntity());
		
		System.out.println(mdto.toEntity().getPassword());
		
		Members members = membersRepository.findByEmail(mdto.getEmail()).get();
		
		madto.setMembersId(members.getMembersId());
		
        if (madto.getJibunAddress() == null || madto.getJibunAddress().equals("")) {
        	madto.setJibunAddress(" ");
        }

        if (madto.getAddressReference() == null || madto.getAddressReference().equals("")) {
        	madto.setAddressReference(" ");
        }

        if (madto.getDetailAddress() == null || madto.getDetailAddress().equals("")) {
        	madto.setDetailAddress(" ");
        }

		memberAddressRepository.save(madto.toEntity(members));

		return "redirect:/main/";
	}
	
//	@GetMapping("/logout")
//	public String logout() {
//		
//		
//		
//		return "main/main";
//	}
	

}
