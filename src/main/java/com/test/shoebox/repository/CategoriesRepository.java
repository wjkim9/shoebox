package com.test.shoebox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Long>{

	List<Categories> findAllByOrderByCategoriesIdAsc();

}
