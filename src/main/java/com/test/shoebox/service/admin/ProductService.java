package com.test.shoebox.service.admin;

import com.test.shoebox.dto.ProductDTO;
import com.test.shoebox.dto.ProductFormDTO;
import com.test.shoebox.dto.ProductStockDTO;
import com.test.shoebox.entity.*;
import com.test.shoebox.repository.*;
//import com.test.shoebox.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;
    private final ProductImageRepository productImageRepository;
    private final BrandRepository brandRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductGroupRepository productGroupRepository;

    // private final FileUploadUtil fileUploadUtil;

    @Transactional
    public void addProduct(ProductDTO dto) {
        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("브랜드 없음"));
        Categories category = categoriesRepository.findById(dto.getCategoriesId())
                .orElseThrow(() -> new RuntimeException("카테고리 없음"));
        ProductGroup group = productGroupRepository.findById(dto.getProductGroupId())
                .orElseThrow(() -> new RuntimeException("상품 그룹 없음"));

        dto.setProductRegisterDate(LocalDateTime.now());

        // 1. 상품 저장
        Product product = dto.toEntity(brand, category, group);
        productRepository.save(product);

        // 2. 재고 저장
        for (int i = 0; i < dto.getSizes().size(); i++) {
            ProductStock stock = ProductStock.builder()
                    .product(product)
                    .shoeSize(dto.getSizes().get(i))
                    .stockQuantity(dto.getSizeStocks().get(i))
                    .build();
            productStockRepository.save(stock);
        }

      /*  // 3. 이미지 저장
        String mainFile = fileUploadUtil.upload(dto.getMainImage());
        productImageRepository.save(new ProductImage(mainFile, 0, product));

        if (dto.getAdditionalImages() != null) {
            for (int i = 0; i < dto.getAdditionalImages().size(); i++) {
                String fileName = fileUploadUtil.upload(dto.getAdditionalImages().get(i));
                productImageRepository.save(new ProductImage(fileName, i + 1, product));
            }
        }*/
    }





        public void registerProduct(ProductDTO dto) {
            // 1. 연관 엔티티 조회
            Brand brand = brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new IllegalArgumentException("브랜드 없음"));
            Categories categories = categoriesRepository.findById(dto.getCategoriesId())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));
            ProductGroup group = productGroupRepository.findById(dto.getProductGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("그룹 없음"));

            // 2. 상품 저장
            Product product = dto.toEntity(brand, categories, group);
            product.setProductRegisterDate(LocalDateTime.now());
            productRepository.save(product);

            // 3. 사이즈 및 재고 저장 (ProductStock 기준)
            List<String> sizes = dto.getSizes();
            List<Integer> stocks = dto.getSizeStocks();
            for (int i = 0; i < sizes.size(); i++) {
                ProductStock stock = ProductStock.builder()
                        .product(product)
                        .shoeSize(sizes.get(i))
                        .stockQuantity(stocks.get(i))
                        .build();
                productStockRepository.save(stock);
            }

        }

    @Transactional
    public Product registerProduct(ProductFormDTO dto) {

        // 1. FK 조회
        Brand brand = brandRepository.findById(dto.getBrandId()).orElseThrow();
        Categories cat = categoriesRepository.findById(dto.getCategoriesId()).orElseThrow();
        ProductGroup group = productGroupRepository.findById(dto.getProductGroupId()).orElseThrow();

        // 2. Product 엔티티 생성
        Product product = Product.builder()
                .productName(dto.getProductName())
                .productId(dto.getProductId())
                .productPrice(dto.getPrice())
                .discountRate(dto.getDiscountRate())
                .targetCustomerType(dto.getTargetCustomerType())
                .productRegisterDate(LocalDateTime.now())
                .brand(brand)
                .categories(cat)
                .productGroup(group)
                .build();

        productRepository.save(product);

        // 3. 사이즈 & 재고 저장
        List<String> sizes = dto.getSizes();
        List<Integer> stocks = dto.getSizeStocks();

        for (int i = 0; i < sizes.size(); i++) {
            ProductStock stock = ProductStock.builder()
                    .shoeSize(sizes.get(i))
                    .stockQuantity(stocks.get(i))
                    .product(product)
                    .build();
            productStockRepository.save(stock);
        }

         /*4. 이미지 저장
        if (dto.getMainImage() != null && !dto.getMainImage().isEmpty()) {
            ProductImage main = ProductImage.builder()
                    .imageOrder(0)
                    .originalName(dto.getMainImage().getOriginalFilename())
                    .product(product)
                    .build();
            productImageRepository.save(main);
        }

        if (dto.getAdditionalImages() != null) {
            for (int i = 0; i < dto.getAdditionalImages().size(); i++) {
                MultipartFile file = dto.getAdditionalImages().get(i);
                if (!file.isEmpty()) {
                    ProductImage image = ProductImage.builder()
                            .imageOrder(i + 1)
                            .originalName(file.getOriginalFilename())
                            .product(product)
                            .build();
                    productImageRepository.save(image);
                }
            }
        }*/

        return product;
    }



    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
    }

   /* public List<String> getAdditionalImages(Long productId) {
        return productImageRepository.findByProductIdAndImageType(productId, 1)
                .stream().map(ProductImage::getFileName).collect(Collectors.toList());
    }
    public List<ProductStockDTO> getStocksByProductId(Long productId) {
        return productStockRepository.findByProduct_ProductId(productId)
                .stream().map(ProductStock::toDTO).collect(Collectors.toList());
    }*/


}