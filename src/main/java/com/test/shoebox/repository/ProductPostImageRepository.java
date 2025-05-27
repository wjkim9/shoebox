package com.test.shoebox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.ProductPost;
import com.test.shoebox.entity.ProductPostImage;

public interface ProductPostImageRepository extends JpaRepository<ProductPostImage, Long> {

	List<ProductPostImage> findByProductPost(ProductPost productPost);

}
