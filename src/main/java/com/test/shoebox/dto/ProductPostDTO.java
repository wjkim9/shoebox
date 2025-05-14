package com.test.shoebox.dto;

import java.time.LocalDateTime;

import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductPost;

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
public class ProductPostDTO {
    private Long productPostId;

    private String content;

    private LocalDateTime postDate;

    private Integer viewCount;

    private Long productId;

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
