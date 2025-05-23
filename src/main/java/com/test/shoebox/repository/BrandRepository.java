package com.test.shoebox.repository;

import com.test.shoebox.dto.BrandDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Brand;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long>{
    @Query("SELECT new com.test.shoebox.dto.BrandDTO(b.brandId, b.brandName) FROM Brand b")
    List<BrandDTO> findAllBrandDTO();

}
