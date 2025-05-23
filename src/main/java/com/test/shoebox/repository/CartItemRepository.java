package com.test.shoebox.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.CartItem;
import com.test.shoebox.entity.Members;
import com.test.shoebox.entity.ProductStock;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	Optional<CartItem> findByMembersAndProductStock(Members members, ProductStock productStock);

}
