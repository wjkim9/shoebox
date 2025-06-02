package com.test.shoebox.common;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.test.shoebox.service.main.CustomOAuth2User;

/**
 * 전역 컨트롤러 어드바이스 클래스입니다.
 * <p>
 * 모든 컨트롤러에 공통적으로 인증 사용자 정보를 전달합니다.
 * Thymeleaf 등에서 {@code loginMember}라는 이름으로
 * 로그인한 사용자 정보를 사용할 수 있습니다.
 * </p>
 * 
 * @author 김원중
 */
@ControllerAdvice
public class GlobalControllerAdvice {

	/**
     * 현재 인증된 사용자 정보를 모든 View(Model)에 {@code loginMember}라는 이름으로 등록합니다.
     *
     * @param oAuth2User 인증된 OAuth2 사용자 정보
     * @return 현재 로그인한 사용자 정보
     */
    @ModelAttribute("loginMember")
    public CustomOAuth2User loginUser(@AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        return oAuth2User;
    }
}
