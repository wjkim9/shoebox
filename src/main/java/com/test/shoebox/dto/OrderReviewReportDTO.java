package com.test.shoebox.dto;

import java.time.LocalDateTime;

import com.test.shoebox.entity.OrderReview;
import com.test.shoebox.entity.OrderReviewReport;

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
public class OrderReviewReportDTO {
    private Long orderReviewReportId;

    private String reportReason;

    private LocalDateTime registerDate;

    private Long orderReviewId;

    public OrderReviewReport toEntity(OrderReview orderReview) {
        return OrderReviewReport.builder()
                .orderReviewReportId(this.orderReviewReportId)
                .reportReason(this.reportReason)
                .registerDate(this.registerDate)
                .orderReview(orderReview)
                .build();
    }
}
