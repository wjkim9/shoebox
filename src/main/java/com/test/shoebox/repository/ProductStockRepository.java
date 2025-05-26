package com.test.shoebox.repository;

import com.test.shoebox.entity.ProductStock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    @Query("SELECT ps.product.productId, SUM(ps.stockQuantity) " +
            "FROM ProductStock ps " +
            "WHERE ps.product.productId IN :productIds " +
            "GROUP BY ps.product.productId")
    List<Object[]> sumStockByProductIds(@Param("productIds") List<Long> productIds);





        // 사이즈 중복 없이 조회
        @Query("SELECT DISTINCT ps.shoeSize FROM ProductStock ps WHERE ps.product.productId = :productId")
        List<String> findDistinctSizesByProductId(@Param("productId") Long productId);

        // 전체 재고 리스트 조회
        List<ProductStock> findByProduct_ProductId(Long productId); // ← 여기가 핵심!
    }



