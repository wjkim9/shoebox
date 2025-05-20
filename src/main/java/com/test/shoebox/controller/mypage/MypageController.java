package com.test.shoebox.controller.mypage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.shoebox.dto.CartItemDTO;
import com.test.shoebox.entity.CartItem;
import com.test.shoebox.entity.Members;
import com.test.shoebox.repository.CartItemRepository;

@Controller
@RequestMapping("/mypage")
public class MypageController {
	
    private final CartItemRepository cartItemRepository;

	@GetMapping("/mypage")
	public String mypage(Model model) {
		
		return "mypage/mypage";
	}

	//생성자 주입
	public MypageController(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }


    @GetMapping("/cart")
    public String cart(Model model) {
        try {
            // 임시로 membersId를 1L로 설정 (테스트용)
            Long membersId = 1L;

            List<CartItem> cartItems = cartItemRepository.findByMembersIdWithProductAndImages(membersId);
            model.addAttribute("cartItems", cartItems);

            int totalAmount = cartItems.stream()
                    .mapToInt(item -> item.getQuantity() * item.getProductStock().getProduct().getProductPrice())
                    .sum();
            model.addAttribute("totalAmount", totalAmount);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("cartItems", new ArrayList<>());
            model.addAttribute("totalAmount", 0);
        }

        return "mypage/cart";
    }


}
