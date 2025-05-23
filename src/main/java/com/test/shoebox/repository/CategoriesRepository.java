package com.test.shoebox.repository;

import com.test.shoebox.dto.CategoriesDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Categories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long>{
    @Query("SELECT new com.test.shoebox.dto.CategoriesDTO(c.categoriesId, c.categoriesName, c.picName) FROM Categories c")
    List<CategoriesDTO> findAllCategoriesDTO();

}
