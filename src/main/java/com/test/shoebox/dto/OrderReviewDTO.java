package com.test.shoebox.dto;

import java.time.LocalDateTime;

import com.test.shoebox.entity.OrderReview;
import com.test.shoebox.entity.ProductStockOrder;

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
public class OrderReviewDTO {
	private Long orderReviewId;

    private String content;

    private Double rating;

    private Integer likeCount;

    private LocalDateTime writeDate;

    private Integer isBlind;

    private Long productStockOrderId;

    public OrderReview toEntity(ProductStockOrder productStockOrder) {
        return OrderReview.builder()
                .orderReviewId(this.orderReviewId)
                .content(this.content)
                .rating(this.rating)
                .likeCount(this.likeCount)
                .writeDate(this.writeDate)
                .isBlind(this.isBlind)
                .productStockOrder(productStockOrder)
                .build();
    }
}
