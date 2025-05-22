package com.test.shoebox.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MYOrderReviewMapDTO {

	private String productId;
	
	private String shoeSize;
	private String quantity;
	private String orderreviewId;
	private String content;
	private String rating;
	private String likeCount;
	private String writeDate;
	private String isBlind;
	private String fileName;
	private String nickname;
	private String membersId;
	
}
