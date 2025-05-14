package com.test.shoebox.entity;

import com.test.shoebox.dto.OrderReviewImageDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OrderReviewImage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderreviewimage_seq_generator")
    @SequenceGenerator(name = "orderreviewimage_seq_generator", sequenceName = "orderreviewimage_seq", allocationSize = 1)
    @Column(name = "orderreviewimage_id")
    private Long orderReviewImageId;

    @Column(name = "file_name", nullable = false, length = 200)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "orderreview_id", nullable = false)
    private OrderReview orderReview;

    public OrderReviewImageDTO toDTO() {
        return OrderReviewImageDTO.builder()
                .orderReviewImageId(this.orderReviewImageId)
                .fileName(this.fileName)
                .orderReviewId(this.orderReview.getOrderReviewId())
                .build();
    }
}
