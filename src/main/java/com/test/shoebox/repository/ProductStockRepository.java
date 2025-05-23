package com.test.shoebox.repository;

import com.test.shoebox.entity.ProductStock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    @Query("SELECT ps.product.productId, SUM(ps.stockQuantity) " +
            "FROM ProductStock ps " +
            "WHERE ps.product.productId IN :productIds " +
            "GROUP BY ps.product.productId")
    List<Object[]> sumStockByProductIds(@Param("productIds") List<Long> productIds);
}
