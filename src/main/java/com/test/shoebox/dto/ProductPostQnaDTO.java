package com.test.shoebox.dto;

import java.time.LocalDateTime;

import com.test.shoebox.entity.Members;
import com.test.shoebox.entity.ProductPost;
import com.test.shoebox.entity.ProductPostQna;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ProductPostQnaDTO {
	private Long productPostQnaId;
	private String title;
	private String content;
	private LocalDateTime writeDate;
	private String answerTitle;
	private String answerContent;
	private LocalDateTime answerDate;
	private Long productPostId;
	private Long membersId;
	
	public ProductPostQna toEntity(ProductPost productPost,Members members) {
        return ProductPostQna.builder()
                .productPostQnaId(this.productPostQnaId)
                .title(this.title)
                .content(this.content)
                .writeDate(this.writeDate)
                .answerTitle(this.answerTitle)
                .answerContent(this.answerContent)
                .answerDate(this.answerDate)
                .productPost(productPost)
                .members(members)
                .build();
    }

}
