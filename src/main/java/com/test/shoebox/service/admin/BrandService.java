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

    public List<BrandDTO> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();

        return brands.stream()
                .map(brand -> new BrandDTO(brand.getBrandId(), brand.getBrandName()))
                .collect(Collectors.toList());
    }

    public BrandDTO getById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다. ID: " + id));
        return new BrandDTO(brand.getBrandId(), brand.getBrandName());
    }
}
