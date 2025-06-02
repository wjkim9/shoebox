package com.test.shoebox.controller.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.shoebox.dto.BrandDTO;
import com.test.shoebox.dto.CategoriesDTO;
import com.test.shoebox.dto.DetailMap;
import com.test.shoebox.dto.MYOrderReviewMapDTO;
import com.test.shoebox.dto.MYProductPostQnaMapDTO;
import com.test.shoebox.dto.OrderForm;
import com.test.shoebox.dto.OrderItemDTO;
import com.test.shoebox.dto.ProductDTO;
import com.test.shoebox.dto.ProductGroupDTO;
import com.test.shoebox.dto.ProductImageDTO;
import com.test.shoebox.dto.ProductPostDTO;
import com.test.shoebox.dto.ProductPostImageDTO;
import com.test.shoebox.dto.ProductStockDTO;
import com.test.shoebox.entity.CartItem;
import com.test.shoebox.entity.Members;
import com.test.shoebox.entity.ProductImage;
import com.test.shoebox.entity.ProductPost;
import com.test.shoebox.entity.ProductPostImage;
import com.test.shoebox.entity.ProductStock;
import com.test.shoebox.mapper.ProductPostMapper;
import com.test.shoebox.repository.CartItemRepository;
import com.test.shoebox.repository.CustomDetailRepository;
import com.test.shoebox.repository.MembersRepository;
import com.test.shoebox.repository.ProductImageRepository;
import com.test.shoebox.repository.ProductPostImageRepository;
import com.test.shoebox.repository.ProductStockRepository;
import com.test.shoebox.service.main.CustomOAuth2User;

import lombok.RequiredArgsConstructor;

/**
 * 상품 상세 페이지 관련 요청을 처리하는 컨트롤러입니다.
 * 
 * <p>다음과 같은 기능을 제공합니다:
 * <ul>
 *   <li>상품 상세 페이지 조회</li>
 *   <li>상품을 장바구니에 추가</li>
 *   <li>장바구니 테스트용 POST 처리</li>
 * </ul>
 * </p>
 *
 * @author 김원중
 */
@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class DetailController {

	private final CustomDetailRepository customDetailRepository;
	private final ProductImageRepository productImageRepository;
	private final ProductPostImageRepository productPostImageRepository;
	private final ProductStockRepository productStockRepository;
	private final ProductPostMapper productPostMapper;

    private final CartItemRepository cartItemRepository;
    private final MembersRepository membersRepository;
	
    /**
     * 상품 상세 페이지를 출력합니다.
     * 
     * <p>상품 게시글 ID를 기반으로 관련된 모든 상품 정보(상품, 카테고리, 브랜드, 이미지, 재고, Q&A 등)를 조회하여
     * 모델에 담아 반환합니다.</p>
     *
     * @param productPostId 조회할 상품 게시글 ID
     * @param model 모델 객체
     * @return 상세 페이지 뷰 이름
     */
	@GetMapping("/detailpage")
	public String detailpage(@RequestParam("productPostId") String productPostId, Model model) {
		
		//기본 상품게시글 정보
		ProductPost productPost = customDetailRepository.findById(productPostId);

		ProductPostDTO productPostDTO = productPost.toDTO();
		CategoriesDTO categoriesDTO = productPost.getProduct().getCategories().toDTO();
		ProductGroupDTO productGroupDTO = productPost.getProduct().getProductGroup().toDTO();
		BrandDTO brandDTO = productPost.getProduct().getBrand().toDTO();
		ProductDTO productDTO = productPost.getProduct().toDTO();
		
//		System.out.println(productPostDTO.getProductPostId());
//		System.out.println(categoriesDTO.getCategoriesName());
//		System.out.println(productGroupDTO.getProductGroupName());
//		System.out.println(brandDTO.getBrandName());
		
		model.addAttribute("productPostDTO", productPostDTO);
		model.addAttribute("categoriesDTO", categoriesDTO);
		model.addAttribute("productGroupDTO", productGroupDTO);
		model.addAttribute("brandDTO", brandDTO);
		model.addAttribute("productDTO", productDTO);
		
		//상품사진(선택한 상품)
		List<ProductImage> productImageList = productImageRepository.findByProductOrderBySortOrderAsc(productPost.getProduct());

		List<ProductImageDTO> productImageDTOList = new ArrayList<>();

		for (ProductImage pi : productImageList) {
			//System.out.println(pi.getFileName());

			ProductImageDTO dto = pi.toDTO();

			productImageDTOList.add(dto);
		}

		model.addAttribute("productImageDTOList", productImageDTOList);

		//첫번째 사진 꺼내기
		ProductImageDTO firstImage = productImageDTOList.get(0);
		
		model.addAttribute("firstImage", firstImage);
		
		//상품게시글사진
		List<ProductPostImage> productPostImageList = productPostImageRepository.findByProductPost(productPost);
		
		List<ProductPostImageDTO> productPostImageDTOList = new ArrayList<>();

		for (ProductPostImage ppi : productPostImageList) {
			//System.out.println(ppi.getFileName());
			
			ProductPostImageDTO dto = ppi.toDTO();
			
			productPostImageDTOList.add(dto);
		}
		
		model.addAttribute("productPostImageDTOList", productPostImageDTOList);
		
		//상품재고 리스트 가져오기
		List<ProductStock> productStockList = productStockRepository.findByProduct(productPost.getProduct());
		
		List<ProductStockDTO> productStockDTOList = new ArrayList<>();
		
		for (ProductStock ps : productStockList) {
			ProductStockDTO dto = ps.toDTO();

			productStockDTOList.add(dto);
		}

		model.addAttribute("productStockDTOList", productStockDTOList);
		
		//상품게시글Q&A
		List<MYProductPostQnaMapDTO> mYProductPostQnaMapDTOList = productPostMapper.getProductPostQna(productPostId);
		
//		for (MYProductPostQnaMapDTO dto : mYProductPostQnaMapDTOList) {
//			System.out.println(dto.getTitle());
//		}
		
		model.addAttribute("mYProductPostQnaMapDTOList", mYProductPostQnaMapDTOList);
		
		//상품그룹 자식(상품사진, 상품게시글)리스트 가져오기
		List<DetailMap> productGroupMapList = productPostMapper.detailtest(productPostId);

		model.addAttribute("productGroupMapList", productGroupMapList);
		
		//상품게시글에 해당하는 상품id 얻어오기
		String productId = productPost.getProduct().getProductId() + "";
		
		//상품 자식(상품재고, 상품재고_주문, 주문후기, 주문후기사진, 주문, 회원) 리스트 가져오기
		List<MYOrderReviewMapDTO> mYOrderReviewMapDTOList = productPostMapper.getOrderReview(productId);
		
		model.addAttribute("mYOrderReviewMapDTOList", mYOrderReviewMapDTOList);
		
//		for (MYOrderReviewMapDTO dto : mYOrderReviewMapDTOList) {
//			System.out.println(dto.getContent());
//		}

		//평균 별점 가져오기
		String rating = productPostMapper.getAvgRating(productId);
		
		model.addAttribute("rating", rating);
		
		//System.out.println("별점~~~" + rating);

		return "main/detailpage";
	}
	
	/**
     * 주문 항목 데이터를 테스트로 받아 출력합니다.
     * 
     * <p>AJAX 테스트를 위한 임시 API이며, 실제 주문 처리 로직은 포함되어 있지 않습니다.</p>
     *
     * @param orderForm 주문 항목 폼
     * @param model 모델 객체
     * @return 리다이렉트 경로 문자열
     */
	@PostMapping("/test")
	@ResponseBody
	public String test(@RequestBody OrderForm orderForm, Model model) {
	    List<OrderItemDTO> items = orderForm.getItems();

	    for (OrderItemDTO item : items) {
	        //System.out.println("상품: " + item.getProductStockId() + ", 수량: " + item.getQty());
	    }

	    model.addAttribute("items", items);
	    return "redirect:/main/"; // 또는 redirect
	}
	
	/**
     * 로그인된 사용자의 장바구니에 상품을 추가합니다.
     * 
     * <p>상품이 이미 장바구니에 있을 경우 수량을 증가시키고,
     * 없을 경우 새로 추가합니다. 로그인하지 않은 경우에는
     * {@code needLogin = true}를 반환하여 프론트에서 로그인 유도 가능.</p>
     *
     * @param orderForm 장바구니에 담을 상품 목록
     * @param user 현재 로그인한 사용자 정보
     * @return 장바구니 추가 결과 Map
     */
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> addToCart(@RequestBody OrderForm orderForm, @AuthenticationPrincipal CustomOAuth2User user) {

		Map<String, Object> result = new HashMap<>();
		
		//회원, 비회원 구분 >> 비회원 => 로그인
	    if (user == null || user.getMembersDTO() == null) {
	        result.put("success", false);
	        result.put("needLogin", true); // JS에서 확인용
	        return result;
	    }
		
		Long memberId = user.getMembersDTO().getMembersId();
		
		List<OrderItemDTO> items = orderForm.getItems();
		
		Members members = membersRepository.findById(memberId).get();
		
	    try {
	    	
	    	for (OrderItemDTO item : items) {
	    		CartItem entity = new CartItem();
	    		
	    		ProductStock productStock = productStockRepository.findById(item.getProductStockId()).get();
	    		entity.setQuantity(item.getQty());
	    		entity.setMembers(members);
	    		entity.setProductStock(productStock);
	    		
	    		Optional<CartItem> existing = cartItemRepository.findByMembersAndProductStock(members, productStock);
	    		
	    		if (existing.isPresent()) {
	    			existing.get().setQuantity(existing.get().getQuantity() + item.getQty());
	    			cartItemRepository.save(existing.get());
	    			
	    		} else {
	    			
	    			cartItemRepository.save(entity);
	    		}
		    }

	        result.put("success", true);
	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("message", e.getMessage());
	    }

	    return result;
	}

}
