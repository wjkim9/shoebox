package com.test.shoebox.repository;

import java.util.List;
import java.util.Optional;

import com.test.shoebox.dto.BrandDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long>{

	Optional<Brand> findByBrandName(String item);

	List<Brand> findAllByOrderByBrandNameAsc();

	Optional<Brand> findBrandNameByBrandId(Long brandId);

    List<BrandDTO> findAllBrandDTO();
}
