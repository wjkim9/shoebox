package com.test.shoebox.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.test.shoebox.dto.ProductDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_generator")
    @SequenceGenerator(name = "product_seq_generator", sequenceName = "product_seq", allocationSize = 1)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @Column(name = "discount_rate", nullable = false)
    private Double discountRate;

    @Column(name = "target_customer_type", nullable = false, length = 50)
    private String targetCustomerType;

    @Column(name = "product_register_date", nullable = false)
    private LocalDateTime productRegisterDate;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "categories_id", nullable = false)
    private Categories categories;

    @ManyToOne
    @JoinColumn(name = "productgroup_id", nullable = false)
    private ProductGroup productGroup;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductPost> productPost = new ArrayList<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductStock> productStock = new ArrayList<>();
    

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;


    public ProductDTO toDTO() {
        return ProductDTO.builder()
                .productId(this.productId)
                .productName(this.productName)
                .productPrice(this.productPrice)
                .discountRate(this.discountRate)
                .targetCustomerType(this.targetCustomerType)
                .productRegisterDate(this.productRegisterDate)
                .brandId(this.brand.getBrandId())
                .categoriesId(this.categories.getCategoriesId())
                .productGroupId(this.productGroup.getProductGroupId())
                .build();
    }
    
    @PrePersist
    public void prePersist() {
        if (this.productRegisterDate == null) {
            this.productRegisterDate = LocalDateTime.now();
        }
    }

}