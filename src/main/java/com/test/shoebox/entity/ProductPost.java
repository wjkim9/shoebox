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

/**
 * 상품 게시글 정보를 나타내는 JPA 엔티티 클래스입니다.
 * 
 * <p>
 * 상품과 연결된 게시글의 내용, 게시일, 조회수 등의 정보를 포함하며,
 * 연관 관계로 {@link Product} 엔티티와 다대일(N:1) 관계를 가집니다.
 * </p>
 * 
 * <p>
 * 게시글 작성 시 {@code postDate}와 {@code viewCount}는 자동으로 초기화됩니다.
 * </p>
 * 
 * @author 김원중
 */
@Entity
@Table(name = "ProductPost")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPost {
	/**
     * 상품 게시글의 고유 ID입니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productpost_seq_generator")
    @SequenceGenerator(name = "productpost_seq_generator", sequenceName = "productpost_seq", allocationSize = 1)
    @Column(name = "productpost_id")
    private Long productPostId;

    /**
     * 게시글의 내용입니다.
     */
    @Column(name = "content", nullable = false)
    private String content;

    /**
     * 게시글 작성일자입니다.
     * {@link #prePersist()} 메서드에 의해 기본값으로 현재 시간이 설정됩니다.
     */
    @Column(name = "post_date", nullable = false)
    private LocalDateTime postDate;

    /**
     * 게시글의 조회수입니다.
     * 기본값은 0이며, {@link #prePersist()}에서 설정됩니다.
     */
    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    /**
     * 해당 게시글이 속한 상품 엔티티입니다.
     */
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * 이 엔티티를 DTO 객체로 변환합니다.
     *
     * @return 변환된 {@link ProductPostDTO} 객체
     */
    public ProductPostDTO toDTO() {
        return ProductPostDTO.builder()
                .productPostId(this.productPostId)
                .content(this.content)
                .postDate(this.postDate)
                .viewCount(this.viewCount)
                .productId(this.product.getProductId())
                .build();
    }

    /**
     * 영속화 전 자동으로 호출되어 게시일(postDate)과 조회수(viewCount)를 초기화합니다.
     * 
     * <p>
     * - postDate가 비어 있을 경우 현재 시간으로 설정<br>
     * - viewCount가 null일 경우 0으로 설정
     * </p>
     */
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
