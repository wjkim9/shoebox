package com.test.shoebox.dto;

import java.time.LocalDateTime;

import com.test.shoebox.entity.Members;
import com.test.shoebox.entity.PointTransaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointTransactionDTO {
	private Long pointTransactionId;

    private String transactionType;

    private Integer transactionPoint;

    private String reason;

    private LocalDateTime transactionDatetime;

    private Long membersId;
    
    public PointTransaction toEntity(Members members) {
        return PointTransaction.builder()
                .pointTransactionId(this.pointTransactionId)
                .transactionType(this.transactionType)
                .transactionPoint(this.transactionPoint)
                .reason(this.reason)
                .transactionDatetime(this.transactionDatetime)
                .members(members)
                .build();
    }
}
