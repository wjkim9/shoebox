package com.test.shoebox.repository;

import com.test.shoebox.dto.BrandDTO;
import com.test.shoebox.entity.Categories;
import com.test.shoebox.entity.ProductGroup;
import com.test.shoebox.entity.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Brand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>{
    @Query("SELECT new com.test.shoebox.dto.BrandDTO(b.brandId, b.brandName) FROM Brand b")
    List<BrandDTO> findAllBrandDTO();


    // CategoryRepository.java
    @Repository
    public interface CategoryRepository extends JpaRepository<Categories, Long> {
    }

    // ProductGroupRepository.java
    @Repository
    public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {
    }

    // ProductStockRepository.java
    @Repository
    public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

        @Query("SELECT DISTINCT ps.shoeSize FROM ProductStock ps WHERE ps.product.productId = :productId")
        List<String> findDistinctSizesByProductId(@Param("productId") Long productId);


        List<ProductStock> findByProductId(Long productId);
    }

}
