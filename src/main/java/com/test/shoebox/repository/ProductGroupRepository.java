package com.test.shoebox.repository;

import com.test.shoebox.dto.ProductDTO;
import com.test.shoebox.dto.ProductGroupDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.ProductGroup;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {
    @Query("SELECT new com.test.shoebox.dto.ProductGroupDTO(p.productGroupId, p.productGroupName) FROM ProductGroup p")
    List<ProductGroupDTO> findAllGroupDTO();
}
