package com.test.shoebox.service.admin;

import com.test.shoebox.dto.MemberWithGradeDTO;
import com.test.shoebox.entity.MemberGrade;
import com.test.shoebox.entity.Members;
import com.test.shoebox.repository.MemberGradeRepository;
import com.test.shoebox.repository.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembersService {

    private final MembersRepository membersRepository;
    private final MemberGradeRepository memberGradeRepository;

    // 회원 목록 조회 (검색 + 페이징)
    public Page<MemberWithGradeDTO> getMembersWithGrade(String keyword, Integer isDeleted, Pageable pageable) {
        return membersRepository.findMembersWithGrade(
                (keyword != null && !keyword.trim().isEmpty()) ? keyword : null,
                isDeleted,
                pageable
        );
    }

    // 특정 회원 조회 (수정폼용)
    public MemberWithGradeDTO getMemberById(Long membersId) {
        return membersRepository.findMemberWithGradeById(membersId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
    }


    // 회원 정보 + 등급 수정
    @Transactional
    public void updateMember(MemberWithGradeDTO dto) {
        // 1. 회원 엔티티 조회
        Members member = membersRepository.findById(dto.getMembersId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        // 2. 회원 기본 정보 수정
        member.setName(dto.getName());
        member.setNickname(dto.getNickname());
        member.setEmail(dto.getEmail());
        member.setContact(dto.getContact());
        member.setFootSize(dto.getFootSize());
        member.setPoint(dto.getPoint());
        member.setIsDeleted(dto.getIsDeleted());

        membersRepository.save(member);

        // 3. 등급 정보 수정 (기존 등급 있으면 수정, 없으면 새로 생성)
        Optional<MemberGrade> gradeOpt = memberGradeRepository.findByMembers_MembersId(dto.getMembersId());
        MemberGrade grade = gradeOpt.orElseGet(MemberGrade::new);

        grade.setGradeName(dto.getGradeName());
        grade.setMembers(member); // 연관 관계 설정

        memberGradeRepository.save(grade);
    }
}
