package com.test.shoebox.service.admin;

import java.util.List;
import java.util.Optional;

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
}
