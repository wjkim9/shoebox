package com.test.shoebox.service.admin;

import com.test.shoebox.dto.CategoriesDTO;
import com.test.shoebox.entity.Categories;
import com.test.shoebox.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public CategoriesDTO getById(Long id) {
        Categories entity = categoriesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));
        return entity.toDTO(); //
    }

    public List<CategoriesDTO> getAll() {
        return categoriesRepository.findAll().stream()
                .map(Categories::toDTO)
                .collect(Collectors.toList());
    }
}
