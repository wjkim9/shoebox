package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
