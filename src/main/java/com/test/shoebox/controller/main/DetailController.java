package com.test.shoebox.controller.main;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.shoebox.dto.DetailMap;
import com.test.shoebox.dto.ProductImageDTO;
import com.test.shoebox.dto.ProductPostImageDTO;
import com.test.shoebox.dto.ProductPostQnaDTO;
import com.test.shoebox.dto.ProductStockDTO;
import com.test.shoebox.entity.ProductImage;
import com.test.shoebox.entity.ProductPost;
import com.test.shoebox.entity.ProductPostImage;
import com.test.shoebox.entity.ProductPostQna;
import com.test.shoebox.entity.ProductStock;
import com.test.shoebox.mapper.ProductPostMapper;
import com.test.shoebox.repository.CustomDetailRepository;
import com.test.shoebox.repository.ProductImageRepository;
import com.test.shoebox.repository.ProductPostImageRepository;
import com.test.shoebox.repository.ProductPostQnaRepository;
import com.test.shoebox.repository.ProductStockRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class DetailController {

	private final CustomDetailRepository customDetailRepository;
	private final ProductImageRepository productImageRepository;
	private final ProductPostImageRepository productPostImageRepository;
	private final ProductPostQnaRepository productPostQnaRepository;
	private final ProductStockRepository productStockRepository;
	private final ProductPostMapper productPostMapper;

	@GetMapping("/detailpage")
	public String detailpage(@RequestParam("productPostId") String productPostId, Model model) {
		
		//기본 상품게시글 정보
		ProductPost productPost = customDetailRepository.findById(productPostId);

		System.out.println(productPost.getProductPostId());
		System.out.println(productPost.getProduct().getProductName());
		System.out.println(productPost.getProduct().getCategories().getCategoriesName());
		System.out.println(productPost.getProduct().getProductGroup().getProductGroupName());
		System.out.println(productPost.getProduct().getBrand().getBrandName());
		
		//상품사진(선택한 상품)
		List<ProductImage> productImageList = productImageRepository.findByProductOrderBySortOrderAsc(productPost.getProduct());

		List<ProductImageDTO> productImageDTOList = null;

		for (ProductImage pi : productImageList) {
			System.out.println(pi.getFileName());

			ProductImageDTO dto = pi.toDTO();

			productImageDTOList.add(dto);
		}

		//상품게시글사진
		List<ProductPostImage> productPostImageList = productPostImageRepository.findByProductPost(productPost);
		
		List<ProductPostImageDTO> productPostImageDTOList = null;

		for (ProductPostImage ppi : productPostImageList) {
			System.out.println(ppi.getFileName());
			
			ProductPostImageDTO dto = ppi.toDTO();
			
			productPostImageDTOList.add(dto);
		}
		
		//상품게시글Q&A
		List<ProductPostQna> productPostQnaList = productPostQnaRepository.findByProductPost(productPost);
		
		List<ProductPostQnaDTO> productPostQnaDTOList = null;

		for (ProductPostQna ppq : productPostQnaList) {
			System.out.println(ppq.getTitle());
			
			ProductPostQnaDTO dto = ppq.toDTO();
			
			productPostQnaDTOList.add(dto);
		}
		
		//상품재고 리스트 가져오기
		List<ProductStock> productStockList = productStockRepository.findByProduct(productPost.getProduct());
		
		List<ProductStockDTO> productStockDTOList = null;
		
		for (ProductStock ps : productStockList) {
			System.out.println("사이즈: " + ps.getShoeSize() + " _ 수량 " + ps.getStockQuantity());
			
			ProductStockDTO dto = ps.toDTO();
			
			productStockDTOList.add(dto);
		}
		
		//상품그룹 자식(상품사진, 상품게시글)리스트 가져오기
		List<DetailMap> productGroupMapList = productPostMapper.detailtest(productPostId);
		
		//상품게시글에 해당하는 상품id 얻어오기
		String productId = productPost.getProduct().getProductId() + "";
		
		//List<> orderReviewList = productPostMapper.getgetOrderReview(productId);
		
		


		return "main/detailpage";
	}
	
}
