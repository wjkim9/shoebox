package com.test.shoebox.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.test.shoebox.dto.DetailMap;
import com.test.shoebox.dto.MYOrderReviewMapDTO;
import com.test.shoebox.dto.MYProductPostQnaMapDTO;

/**
 * 상품 게시글 관련 쿼리를 수행하는 MyBatis Mapper 인터페이스입니다.
 * 
 * <p>
 * ProductPost와 관련된 Q&A, 리뷰, 평점, 상세 정보 등을 조회하는 SQL이 정의됩니다.
 * </p>
 * 
 * <p>주요 기능:</p>
 * <ul>
 *   <li>상품그룹 상세 정보 조회</li>
 *   <li>상품 게시글의 Q&A 조회</li>
 *   <li>주문 및 리뷰 정보 조회</li>
 *   <li>평균 별점 조회</li>
 * </ul>
 * 
 * @author 김원중
 */
@Mapper
public interface ProductPostMapper {
	
	/**
     * 현재 시간을 조회하는 테스트용 쿼리입니다.
     *
     * @return 현재 시간 문자열
     */
	String time();
	
	/**
     * 특정 상품그룹 ID에 해당하는 상세 정보를 조회합니다.
     *
     * @param productgroupId 조회할 상품그룹 ID
     * @return 상품 이미지 및 게시글 정보 리스트
     */
	List<DetailMap> detailtest(String productgroupId);
	
	/**
     * 특정 상품 게시글에 대한 Q&A 목록을 조회합니다.
     *
     * @param productPostId 상품 게시글 ID
     * @return Q&A 정보 DTO 리스트
     */
	List<MYProductPostQnaMapDTO> getProductPostQna(String productPostId);
	
	/**
     * 특정 상품에 대한 주문 및 리뷰 정보를 조회합니다.
     *
     * @param productId 상품 ID
     * @return 리뷰 관련 정보 DTO 리스트
     */
	List<MYOrderReviewMapDTO> getOrderReview(String productId);
	
	/**
     * 특정 상품에 대한 평균 별점을 조회합니다.
     *
     * @param productId 상품 ID
     * @return 평균 별점 (문자열 형태)
     */
	String getAvgRating(String productId);
}
