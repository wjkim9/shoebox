package com.test.shoebox.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductPostMapper {
	
	String time();
	
	List<Map<String, String>> detailtest();
	
}
