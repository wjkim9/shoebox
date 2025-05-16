package com.test.shoebox.service.admin;

import com.test.shoebox.entity.Product;
import com.test.shoebox.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired  // 생성자 주입 (Spring에서 자동 주입)
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();  // JPA 기반 repository
    }
}
