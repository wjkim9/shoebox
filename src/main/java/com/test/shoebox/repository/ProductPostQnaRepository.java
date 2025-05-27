package com.test.shoebox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.ProductPost;
import com.test.shoebox.entity.ProductPostQna;

public interface ProductPostQnaRepository extends JpaRepository<ProductPostQna, Long> {

	List<ProductPostQna> findByProductPost(ProductPost productPost);
	
}
