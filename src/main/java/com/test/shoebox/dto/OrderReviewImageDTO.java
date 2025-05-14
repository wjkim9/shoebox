package com.test.shoebox.dto;


import com.test.shoebox.entity.OrderReview;
import com.test.shoebox.entity.OrderReviewImage;

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
public class OrderReviewImageDTO {
    private Long orderReviewImageId;

    private String fileName;

    private OrderReview orderReview;
    
    private Long orderReviewId;

    public OrderReviewImage toEntity(OrderReview orderReview) {
        return OrderReviewImage.builder()
                .orderReviewImageId(this.orderReviewImageId)
                .fileName(this.fileName)
                .orderReview(orderReview)
                .build();
    }
}
