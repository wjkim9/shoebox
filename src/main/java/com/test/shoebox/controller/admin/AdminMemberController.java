package com.test.shoebox.controller.admin;

import com.test.shoebox.dto.MemberDetailDTO;
import com.test.shoebox.dto.MemberWithGradeDTO;
import com.test.shoebox.service.admin.MembersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class AdminMemberController {

    private final MembersService membersService;

    /**
     * 회원 목록/검색 페이지
     * - /admin/member/list
     * - AJAX(fragment) 및 일반 요청 모두 지원
     */
    @GetMapping("/member-list")
    public String memberList(
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "is_deleted", required = false) Integer isDeleted,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestHeader(value = "X-Requested-With", required = false) String requestedWith,
        Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<MemberWithGradeDTO> memberPage = membersService.getMembersWithGrade(keyword, isDeleted, pageable);

        model.addAttribute("memberPage", memberPage);

        if ("XMLHttpRequest".equals(requestedWith)) {
            return "admin/member/member-list :: content";
        }
        return "admin/member/member-list";
    }
    
    /**
     * 회원 수정 폼 페이지
     */
    @GetMapping("/edit/{id}")
    public String editMemberForm(@PathVariable("id") Long memberId, Model model) {
        MemberWithGradeDTO member = membersService.getMemberById(memberId);
        model.addAttribute("member", member);
        return "admin/member/member-edit";
    }

    /**
     * 회원 정보 업데이트 처리
     */
    @PostMapping("/update")
    public String updateMember(@ModelAttribute MemberWithGradeDTO memberDto) {
        membersService.updateMember(memberDto);
        return "redirect:/admin/member/member-list";
    }
    
    /**
     * 회원 상세 정보 조회
     */
    @GetMapping("/{id}")
    public String memberDetail(@PathVariable("id") Long memberId, Model model) {
        MemberDetailDTO memberDetail = membersService.getMemberDetail(memberId);
        model.addAttribute("memberDetail", memberDetail);
        return "admin/member/member-detail";
    }


}
