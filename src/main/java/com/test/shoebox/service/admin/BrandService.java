package com.test.shoebox.service.admin;

import com.test.shoebox.dto.BrandDTO;
import com.test.shoebox.entity.Brand;
import com.test.shoebox.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandDTO getById(Long id) {
        Brand entity = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("브랜드 없음"));
        return entity.toDTO();
    }

    public List<BrandDTO> getAll() {
        return brandRepository.findAll().stream()
                .map(Brand::toDTO)
                .collect(Collectors.toList());
    }
}


