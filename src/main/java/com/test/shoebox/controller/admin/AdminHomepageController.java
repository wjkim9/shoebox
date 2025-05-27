package com.test.shoebox.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/admin/homepage")
public class AdminHomepageController {

    /** 리뷰 관리 페이지 */
    @GetMapping("/review")
    public String reviewManagement(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        List<Map<String, Object>> reviews = new ArrayList<>();

        // 첫 번째 리뷰
        Map<String, Object> c1 = new HashMap<>();
        c1.put("id", 1L);
        c1.put("reviewerName", "홍길동");
        c1.put("writeDate", new Date());                // 현재 시간
        c1.put("rating", 4.5);
        c1.put("content", "정말 편하고 예뻐요!");
        c1.put("deleted", false);
        reviews.add(c1);

        // 두 번째 리뷰 (삭제된 상태)
        Map<String, Object> c2 = new HashMap<>();
        c2.put("id", 2L);
        c2.put("reviewerName", "이영희");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        c2.put("writeDate", cal.getTime());             // 어제 시간
        c2.put("rating", 3.0);
        c2.put("content", "배송이 느렸어요");
        c2.put("deleted", true);
        reviews.add(c2);

        model.addAttribute("reviews", reviews);


        if ("XMLHttpRequest".equals(requestedWith)) {
            return "admin/homepage/review :: content";
        }
        return "admin/homepage/review";
    }


    /** Q&A 설정 페이지 */
    @GetMapping("/qna")
    public String qnaSettings(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        // TODO: Model에 Q&A 목록, 답변 폼 등 추가

        List<Map<String, Object>> qnas = new ArrayList<>();

        // 첫 번째 문의 (미답변)
        Map<String, Object> q1 = new HashMap<>();
        q1.put("id", 1L);
        q1.put("authorName", "홍길동");
        q1.put("writeDate", new Date());               // 현재 시간
        q1.put("title", "배송 지연 문의");
        q1.put("category", "배송");
        q1.put("answerContent", null);                  // 미답변
        qnas.add(q1);

        // 두 번째 문의 (답변 완료)
        Map<String, Object> q2 = new HashMap<>();
        q2.put("id", 2L);
        q2.put("authorName", "이영희");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -2);
        q2.put("writeDate", cal.getTime());             // 이틀 전 작성일
        q2.put("title", "상품 교환 문의");
        q2.put("category", "교환");
        q2.put("answerContent", "안녕하세요, 교환 처리해 드렸습니다.");  // 답변 내용
        qnas.add(q2);

        model.addAttribute("qnas", qnas);

        if ("XMLHttpRequest".equals(requestedWith)) {
            return "admin/homepage/qna :: content";
        }
        return "admin/homepage/qna";
    }


    /**
     * Q&A 상세페이지 진입 (GET)
     */
    @GetMapping("/details/{id}")
    public String viewQnaDetail(@PathVariable("id") Long id, Model model) {
        Map<String, Object> qna = new HashMap<>();
        qna.put("membersId", 2L);
        qna.put("customerserviceId", id);
        qna.put("authorName", "이영희");
        qna.put("category", "배송");
        qna.put("title", "배송이 너무 늦어요");
        qna.put("content", "5일이 지났는데 아직도 안 와요 😥");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        qna.put("writeDate", LocalDateTime.of(2025, 5, 16, 14, 20).format(formatter));
        qna.put("answerContent", "안녕하세요! 오늘 출고될 예정입니다.");
        qna.put("answerDate", LocalDateTime.of(2025, 5, 17, 10, 30).format(formatter));

        model.addAttribute("qna", qna);
        return "admin/homepage/qna-details"; // Thymeleaf 템플릿 경로
    }

    @PostMapping("/answer")
    public String submitAnswer(@RequestParam("customerserviceId") Long id,
                               @RequestParam("answerContent") String answerContent,
                               RedirectAttributes redirectAttributes) {

        // 실제 DB 저장 대신 로그 출력 또는 임시 처리
        System.out.println("답변 저장: ID = " + id + ", 내용 = " + answerContent);
        redirectAttributes.addFlashAttribute("successMessage", "임시 답변이 등록되었습니다.");

        return "redirect:/admin/homepage-qna/details/" + id;
    }


    /** 공지사항 설정 페이지 */

    @GetMapping("/notice")
    public String viewNoticeList(Model model,@RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        List<Map<String, Object>> notices = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 1; i <= 5; i++) {
            Map<String, Object> notice = new HashMap<>();
            notice.put("id", (long) i);
            notice.put("title", "공지사항 제목 " + i);
            notice.put("author", "관리자");
            notice.put("createdAt", LocalDateTime.of(2025, 5, 19, 9, 30).format(formatter));
            notice.put("viewCount", i * 10);
            notices.add(notice);
        }

        model.addAttribute("notices", notices);

        if ("XMLHttpRequest".equals(requestedWith)) {
            return "admin/homepage/notice :: content";
        }
        return "admin/homepage/notice";  // Thymeleaf 경로
    }


    @GetMapping("/notice/{id}")
    public String viewNoticeDetail(@PathVariable("id") Long id, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Map<String, Object> notice = new HashMap<>();
        notice.put("id", id);
        notice.put("title", "서버 점검 안내");
        notice.put("author", "관리자");
        notice.put("createdAt", LocalDateTime.of(2025, 5, 19, 9, 30).format(formatter));
        notice.put("viewCount", 123);
        notice.put("content", """
            안녕하세요. 고객님 여러분 😊
            
            시스템 안정화를 위한 서버 점검이 아래 일정으로 진행됩니다.

            - 일정: 2025년 5월 20일(화) 02:00 ~ 04:00
            - 영향: 사이트 접속 일시 중단

            이용에 불편을 드려 죄송하며, 더 나은 서비스를 위해 노력하겠습니다.
            감사합니다.
            """);

        model.addAttribute("notice", notice);
        return "admin/homepage/notice-details";
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
