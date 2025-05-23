package com.test.shoebox.repository;

import java.util.List;
import java.util.Optional;

import com.test.shoebox.dto.BrandDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Brand;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long>{
    @Query("SELECT new com.test.shoebox.dto.BrandDTO(b.brandId, b.brandName) FROM Brand b")
    List<BrandDTO> findAllBrandDTO();

	Optional<Brand> findByBrandName(String item);

	List<Brand> findAllByOrderByBrandNameAsc();

	Optional<Brand> findBrandNameByBrandId(Long brandId);

}
