package com.test.shoebox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailDTO {
    // 회원 기본 정보
    private MemberWithGradeDTO memberInfo;
    
    // 배송지 정보
    private List<MemberAddressDTO> addresses;
    
    // 포인트 거래내역
    private List<PointTransactionDTO> pointTransactions;
    
    // 쿠폰 정보
    private List<IssuedCouponDTO> issuedCoupons;
    
    // 채팅 정보
    private List<ChatMessageDTO> chatMessages;
}
