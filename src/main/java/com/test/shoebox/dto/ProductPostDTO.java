package com.test.shoebox.dto;

import java.time.LocalDateTime;

import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductPost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품 게시글 정보를 전달하기 위한 DTO(Data Transfer Object) 클래스입니다.
 * <p>
 * 게시글의 ID, 내용, 게시일, 조회수, 연관된 상품 ID 등의 정보를 담고 있으며,
 * Entity 객체로 변환하는 메서드도 포함되어 있습니다.
 * </p>
 * 
 * @author 김원중
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPostDTO {
	
	/**
     * 상품 게시글의 고유 ID입니다.
     */
    private Long productPostId;

    /**
     * 게시글 내용입니다.
     */
    private String content;

    /**
     * 게시일 (작성일자)입니다.
     */
    private LocalDateTime postDate;

    /**
     * 게시글 조회수입니다.
     */
    private Integer viewCount;

    /**
     * 연관된 상품(Product)의 ID입니다.
     */
    private Long productId;

    /**
     * 이 DTO를 Entity 객체로 변환합니다.
     *
     * @param product 연관된 상품 엔티티
     * @return 변환된 ProductPost 엔티티 객체
     */
    public ProductPost toEntity(Product product) {
        return ProductPost.builder()
                .productPostId(this.productPostId)
                .content(this.content)
                .postDate(this.postDate)
                .viewCount(this.viewCount)
                .product(product)
                .build();
    }
}
