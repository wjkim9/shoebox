package com.test.shoebox.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.test.shoebox.dto.DetailMap;
import com.test.shoebox.dto.MYOrderReviewMapDTO;
import com.test.shoebox.dto.MYProductPostQnaMapDTO;

@Mapper
public interface ProductPostMapper {
	
	String time();
	
	List<DetailMap> detailtest(String productgroupId);
	
	List<MYProductPostQnaMapDTO> getProductPostQna(String productPostId);
	
	List<MYOrderReviewMapDTO> getOrderReview(String productId);
	
	String getAvgRating(String productId);
}
