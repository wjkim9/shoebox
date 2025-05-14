package com.test.shoebox.entity;

import java.time.LocalDateTime;

import com.test.shoebox.dto.OrderReviewReportDTO;

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
@Table(name = "OrderReviewReport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderReviewReport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderreviewreport_seq_generator")
    @SequenceGenerator(name = "orderreviewreport_seq_generator", sequenceName = "orderreviewreport_seq", allocationSize = 1)
    @Column(name = "orderreviewreport_id")
    private Long orderReviewReportId;

    @Column(name = "report_reason", nullable = false, length = 100)
    private String reportReason;

    @Column(name = "register_date", nullable = false)
    private LocalDateTime registerDate;

    @ManyToOne
    @JoinColumn(name = "orderreview_id", nullable = false)
    private OrderReview orderReview;

    public OrderReviewReportDTO toDTO() {
        return OrderReviewReportDTO.builder()
                .orderReviewReportId(this.orderReviewReportId)
                .reportReason(this.reportReason)
                .registerDate(this.registerDate)
                .orderReviewId(this.orderReview.getOrderReviewId())
                .build();
    }
    
    @PrePersist
    public void prePersist() {
        if (this.registerDate == null) {
            this.registerDate = LocalDateTime.now();
        }
    }

}
