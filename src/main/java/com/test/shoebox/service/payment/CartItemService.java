package com.test.shoebox.service.payment;

import com.test.shoebox.entity.CartItem;
import com.test.shoebox.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public List<CartItem> getCartItems(Long memberId) {
        return cartItemRepository.findByMembers_MembersId(memberId);
    }

    @Transactional
    public void updateQuantity(Long cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("장바구니 아이템을 찾을 수 없습니다."));
        
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }
} 