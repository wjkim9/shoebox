package com.test.shoebox.service.main;

public interface OAuth2Response {

	//제공자(naver, google)
	String getProvider();
	
	//제공자에서 발급하는 아이디
	String getProviderId();
	
	//이메일
	String getEmail();
	
	//이름
	String getName();
	
}










	
