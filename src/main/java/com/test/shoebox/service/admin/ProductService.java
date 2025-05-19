package com.test.shoebox.service.admin;

import com.test.shoebox.dto.ProductDTO;
import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.Brand;
import com.test.shoebox.entity.Categories;
import com.test.shoebox.entity.ProductGroup;
import com.test.shoebox.repository.BrandRepository;
import com.test.shoebox.repository.CategoriesRepository;
import com.test.shoebox.repository.ProductGroupRepository;
import com.test.shoebox.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductGroupRepository productGroupRepository;

    public void saveProduct(ProductDTO dto) {
        Brand brand = brandRepository.findById(dto.getBrandId()).orElseThrow();
        Categories categories = categoriesRepository.findById(dto.getCategoriesId()).orElseThrow();
        ProductGroup productGroup = productGroupRepository.findById(dto.getProductGroupId()).orElseThrow();

        Product product = dto.toEntity(brand, categories, productGroup);
        productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
