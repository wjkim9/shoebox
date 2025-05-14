package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.FavoriteBrand;

public interface FavoriteBrandRepository extends JpaRepository<FavoriteBrand, Long>{

}
