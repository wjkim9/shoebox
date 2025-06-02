package com.test.shoebox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductStock;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long>  {

	List<ProductStock> findByProduct(Product product);

    List<Object[]> sumStockByProductIds(List<Long> productIds);

    List<String> findDistinctSizesByProductId(Long attr0);

    List<ProductStock> findByProduct_ProductId(Long productProductId);
}
