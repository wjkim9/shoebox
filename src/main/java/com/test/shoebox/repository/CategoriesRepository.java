package com.test.shoebox.repository;

import java.util.List;
import java.util.Optional;

import com.test.shoebox.dto.CategoriesDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Categories;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, Long>{
    @Query("SELECT new com.test.shoebox.dto.CategoriesDTO(c.categoriesId, c.categoriesName, c.picName) FROM Categories c")
    List<CategoriesDTO> findAllCategoriesDTO();

	List<Categories> findAllByOrderByCategoriesIdAsc();

	Optional<Categories> findCategoriesNameByCategoriesId(Long categoriesId);

	

}
