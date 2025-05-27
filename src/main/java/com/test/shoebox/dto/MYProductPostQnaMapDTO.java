package com.test.shoebox.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MYProductPostQnaMapDTO {

	private String productpostqnaId;

	private String title;
	private String content;
	private String writeDate;
	private String answerTitle;
	private String answerContent;
	private String answerDate;
	private String productpostId;

	private String nickname;
	private String membersId;
}
