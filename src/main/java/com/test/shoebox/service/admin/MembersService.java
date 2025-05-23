package com.test.shoebox.service.admin;

import com.test.shoebox.dto.*;
import com.test.shoebox.entity.MemberGrade;
import com.test.shoebox.entity.Members;
import com.test.shoebox.repository.*;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembersService {

    private final MembersRepository membersRepository;
    private final MemberGradeRepository memberGradeRepository;

    @Autowired
    private MemberAddressRepository memberAddressRepository;

    @Autowired
    private PointTransactionRepository pointTransactionRepository;

    @Autowired
    private IssuedCouponRepository issuedCouponRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    // 회원 목록 조회
    public Page<MemberWithGradeDTO> getMembersWithGrade(String keyword, Integer isDeleted, Pageable pageable) {
        return membersRepository.findMembersWithGrade(
                (keyword != null && !keyword.trim().isEmpty()) ? keyword : null,
                isDeleted,
                pageable
        );
    }

    // 특정 회원 조회
    public MemberWithGradeDTO getMemberById(Long membersId) {
        return membersRepository.findMemberWithGradeById(membersId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
    }

    // 회원 정보 + 등급 수정
    @Transactional
    public void updateMember(MemberWithGradeDTO dto) {
        Members member = membersRepository.findById(dto.getMembersId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        member.setName(dto.getName());
        member.setNickname(dto.getNickname());
        member.setEmail(dto.getEmail());
        member.setContact(dto.getContact());
        member.setFootSize(dto.getFootSize());
        member.setPoint(dto.getPoint());
        member.setIsDeleted(dto.getIsDeleted());

        membersRepository.save(member);

        Optional<MemberGrade> gradeOpt = memberGradeRepository.findByMembers_MembersId(dto.getMembersId());
        MemberGrade grade = gradeOpt.orElseGet(MemberGrade::new);
        grade.setGradeName(dto.getGradeName());
        grade.setMembers(member);
        memberGradeRepository.save(grade);
    }

    // 회원 상세 정보 조회
    public MemberDetailDTO getMemberDetail(Long membersId) {
        // 1. 회원 기본 정보
        MemberWithGradeDTO memberInfo = getMemberById(membersId);

        // 2. 배송지 정보
        List<Object[]> addressResults = memberAddressRepository.findAddressesByMembersId(membersId);
        List<MemberAddressDTO> addresses = addressResults.stream()
            .map(result -> {
                MemberAddressDTO dto = new MemberAddressDTO();
                if (result[0] instanceof BigDecimal bd0) dto.setMemberAddressId(bd0.longValue());
                
                // 우편번호 처리 부분 수정
                if (result[1] != null) {
                    try {
                        // String으로 변환 후 Integer로 파싱
                        dto.setZipCode(Integer.parseInt(String.valueOf(result[1])));
                    } catch (NumberFormatException e) {
                        System.err.println("우편번호 변환 오류: " + e.getMessage());
                        // 기본값 설정 또는 null로 둘 수 있음
                        dto.setZipCode(null);
                    }
                }
                
                dto.setRoadAddress((String) result[2]);
                dto.setJibunAddress((String) result[3]);
                dto.setDetailAddress((String) result[4]);
                dto.setAddressReference((String) result[5]);
                if (result[6] instanceof BigDecimal bd6) dto.setMembersId(bd6.longValue());
                return dto;
            }).collect(Collectors.toList());

        // 3. 포인트 거래 내역
        List<Object[]> transactionResults = pointTransactionRepository.findTransactionsByMembersId(membersId);
        List<PointTransactionDTO> pointTransactions = transactionResults.stream()
            .map(result -> new PointTransactionDTO(
                toLong(result[0]),
                (String) result[1],
                toInt(result[2]),
                (String) result[3],
                ((Timestamp) result[4]).toLocalDateTime(),
                toLong(result[5])
            )).collect(Collectors.toList());

        // 4. 쿠폰 정보
        List<Object[]> couponResults = issuedCouponRepository.findValidCouponsByMembersId(membersId);
        //FIXME
        List<IssuedCouponDTO> issuedCoupons = couponResults.stream()
            .map(result -> new IssuedCouponDTO(
                toLong(result[0]),
                ((Timestamp) result[1]).toLocalDateTime(),
                ((Timestamp) result[2]).toLocalDateTime(),
                toLong(result[3]),
                toLong(result[4]),
                (String) result[5],
                toDouble(result[6]),
                result[7] != null ? toInt(result[7]) : null,
                result[8] != null ? toInt(result[8]) : null
            )).collect(Collectors.toList());

     // 5. 채팅 정보 조회
        List<Object[]> chatResults = chatMessageRepository.findChatMessagesByMembersId(membersId);
        List<ChatMessageDTO> chatMessages = chatResults.stream()
            .map(result -> {
                String content;
                Object val = result[1];

                if (val instanceof String) {
                    content = (String) val;
                } else if (val != null) {
                    content = val.toString();
                } else {
                    content = null;
                }

                return new ChatMessageDTO(
                    toLong(result[0]),     // chatmessage_id
                    content,               // content (프록시 대응)
                    toInt(result[2]),      // writer_classify
                    ((Timestamp) result[3]).toLocalDateTime(),  // send_datetime
                    toLong(result[4])      // chatroom_id
                );
            }).collect(Collectors.toList());


        // 6. 상세 DTO 조립
        return MemberDetailDTO.builder()
                .memberInfo(memberInfo)
                .addresses(addresses)
                .pointTransactions(pointTransactions)
                .issuedCoupons(issuedCoupons)
                .chatMessages(chatMessages)
                .build();
    }

    // 🔧 변환 유틸
    private Long toLong(Object obj) {
        return obj instanceof BigDecimal bd ? bd.longValue() : (obj instanceof Number n ? n.longValue() : null);
    }

    private Integer toInt(Object obj) {
        return obj instanceof BigDecimal bd ? bd.intValue() : (obj instanceof Number n ? n.intValue() : null);
    }

    private Double toDouble(Object obj) {
        return obj instanceof BigDecimal bd ? bd.doubleValue() : (obj instanceof Number n ? n.doubleValue() : null);
    }
}
