package com.test.shoebox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductStock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

	List<ProductStock> findByProduct(Product product);

    @Query("SELECT ps.product.productId, SUM(ps.stockQuantity) " +
            "FROM ProductStock ps " +
            "WHERE ps.product.productId IN :productIds " +
            "GROUP BY ps.product.productId")
    List<Object[]> sumStockByProductIds(@Param("productIds") List<Long> productIds);
}
