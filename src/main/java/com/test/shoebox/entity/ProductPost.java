package com.test.shoebox.entity;

import java.time.LocalDateTime;

import com.test.shoebox.dto.ProductPostDTO;

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
import lombok.ToString;

@Entity
@Table(name = "ProductPost")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productpost_seq_generator")
    @SequenceGenerator(name = "productpost_seq_generator", sequenceName = "productpost_seq", allocationSize = 1)
    @Column(name = "productpost_id")
    private Long productPostId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "post_date", nullable = false)
    private LocalDateTime postDate;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public ProductPostDTO toDTO() {
        return ProductPostDTO.builder()
                .productPostId(this.productPostId)
                .content(this.content)
                .postDate(this.postDate)
                .viewCount(this.viewCount)
                .productId(this.product.getProductId())
                .build();
    }
    
    @PrePersist
    public void prePersist() {
        if (this.postDate == null) {
            this.postDate = LocalDateTime.now();
        }
        if (this.viewCount == null) {
            this.viewCount = 0;
        }
    }

}
