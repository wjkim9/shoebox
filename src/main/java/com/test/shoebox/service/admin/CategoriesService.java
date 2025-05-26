package com.test.shoebox.service.admin;

import java.util.List;
import java.util.Optional;

import com.test.shoebox.dto.CategoriesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.shoebox.entity.Categories;
import com.test.shoebox.repository.CategoriesRepository;

@Service
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }

    @Transactional
    public Categories save(Categories category) {
        return categoriesRepository.save(category);
    }

    public void deleteById(Long id) {
        categoriesRepository.deleteById(id);
    }

    public Optional<Categories> findById(Long id) {
        return categoriesRepository.findById(id);
    }

    public CategoriesDTO getById(Long id) {
        Categories categories = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 카테고리가 없습니다."));

        CategoriesDTO dto = new CategoriesDTO();
        dto.setCategoriesId(categories.getCategoriesId());
        dto.setCategoriesName(categories.getCategoriesName());
        dto.setPicName(categories.getPicName());
        return dto;
    }


}














