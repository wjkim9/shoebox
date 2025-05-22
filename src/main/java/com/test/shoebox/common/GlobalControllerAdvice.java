package com.test.shoebox.common;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.test.shoebox.service.main.CustomOAuth2User;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("loginMember")
    public CustomOAuth2User loginUser(@AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        return oAuth2User;
    }
}
