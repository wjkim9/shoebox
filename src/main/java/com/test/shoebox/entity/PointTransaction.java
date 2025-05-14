package com.test.shoebox.entity;

import java.time.LocalDateTime;

import com.test.shoebox.dto.PointTransactionDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PointTransaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pointtransaction_seq_generator")
    @SequenceGenerator(name = "pointtransaction_seq_generator", sequenceName = "pointtransaction_seq", allocationSize = 1)
    @Column(name = "pointtransaction_id")
    private Long pointTransactionId;

    @Column(name = "transaction_type", nullable = false, length = 20)
    private String transactionType;

    @Column(name = "transaction_point", nullable = false)
    private Integer transactionPoint;

    @Column(name = "reason", nullable = false, length = 100)
    private String reason;

    @Column(name = "transaction_datetime", nullable = false)
    private LocalDateTime transactionDatetime;

    @ManyToOne
    @JoinColumn(name = "members_id", nullable = false)
    private Members members;

    public PointTransactionDTO toDTO() {
        return PointTransactionDTO.builder()
                .pointTransactionId(this.pointTransactionId)
                .transactionType(this.transactionType)
                .transactionPoint(this.transactionPoint)
                .reason(this.reason)
                .transactionDatetime(this.transactionDatetime)
                .membersId(this.members.getMembersId())
                .build();
    }
    
    @PrePersist
    public void prePersist() {
        if (this.transactionDatetime == null) {
            this.transactionDatetime = LocalDateTime.now();
        }
    }

}

