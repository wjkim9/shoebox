package com.test.shoebox.entity;

import java.time.LocalDateTime;

import com.test.shoebox.dto.OrderReviewDTO;

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
@Table(name = "OrderReview")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderReview {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderreview_seq_generator")
    @SequenceGenerator(name = "orderreview_seq_generator", sequenceName = "orderreview_seq", allocationSize = 1)
    @Column(name = "orderreview_id")
    private Long orderReviewId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    @Column(name = "write_date", nullable = false)
    private LocalDateTime writeDate;

    @Column(name = "is_blind")
    private Integer isBlind;

    @ManyToOne
    @JoinColumn(name = "productstockorder_id", nullable = false)
    private ProductStockOrder productStockOrder;

    public OrderReviewDTO toDTO() {
        return OrderReviewDTO.builder()
                .orderReviewId(this.orderReviewId)
                .content(this.content)
                .rating(this.rating)
                .likeCount(this.likeCount)
                .writeDate(this.writeDate)
                .isBlind(this.isBlind)
                .productStockOrderId(this.productStockOrder.getProductStockOrderId())
                .build();
    }
    
    @PrePersist
    public void prePersist() {
        if (this.writeDate == null) {
            this.writeDate = LocalDateTime.now();
        }
        if (this.isBlind == null) {
            this.isBlind = 0;
        }
    }

}
