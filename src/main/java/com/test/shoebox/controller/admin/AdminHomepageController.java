package com.test.shoebox.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/homepage")
public class AdminHomepageController {

    /** 리뷰 관리 페이지 */
    @GetMapping("/review")
    public String reviewManagement() {
        // TODO: Model에 리뷰 리스트, 페이징 정보 등 추가
        return "admin/homepage/review";
    }

    /** Q&A 설정 페이지 */
    @GetMapping("/qna")
    public String qnaSettings() {
        // TODO: Model에 Q&A 목록, 답변 폼 등 추가
        return "admin/homepage/qna";
    }

    /** 공지사항 설정 페이지 */
    @GetMapping("/notice")
    public String noticeSettings() {
        // TODO: Model에 공지사항 리스트, 등록/수정 폼 등 추가
        return "admin/homepage/notice";
    }

    /** 팝업 공지 설정 페이지 */
    @GetMapping("/popup")
    public String popupSettings() {
        // TODO: Model에 팝업 공지 리스트, 활성화/비활성화 등 추가
        return "admin/homepage/popup";
    }

    /** 배너 등록 페이지 */
    @GetMapping("/banner")
    public String bannerRegistration() {
        // TODO: Model에 배너 목록, 이미지 업로드 기능 등 추가
        return "admin/homepage/banner";
    }

    /** 이벤트/프로모션 등록 페이지 */
    @GetMapping("/event")
    public String eventRegistration() {
        // TODO: Model에 이벤트 리스트, 프로모션 설정 폼 등 추가
        return "admin/homepage/event";
    }

}
