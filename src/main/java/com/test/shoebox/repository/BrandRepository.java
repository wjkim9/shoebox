package com.test.shoebox.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long>{

	Optional<Brand> findByBrandName(String item);

	List<Brand> findAllByOrderByBrandNameAsc();

}
