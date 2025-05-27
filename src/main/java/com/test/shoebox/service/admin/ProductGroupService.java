package com.test.shoebox.service.admin;

import com.test.shoebox.dto.ProductGroupDTO;
import com.test.shoebox.entity.ProductGroup;
import com.test.shoebox.repository.ProductGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductGroupService {
    private final ProductGroupRepository productGroupRepository;

    public ProductGroupDTO getById(Long id) {
        ProductGroup entity = productGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 그룹 없음"));
        return entity.toDTO();
    }

    public List<ProductGroupDTO> getAll() {
        return productGroupRepository.findAll().stream()
                .map(ProductGroup::toDTO)
                .collect(Collectors.toList());
    }
}
