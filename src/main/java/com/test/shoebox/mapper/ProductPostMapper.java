package com.test.shoebox.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.test.shoebox.dto.DetailMap;

@Mapper
public interface ProductPostMapper {
	
	String time();
	
	List<DetailMap> detailtest(String productgroupId);
	
}
