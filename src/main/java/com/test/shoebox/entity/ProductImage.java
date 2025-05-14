package com.test.shoebox.entity;

import com.test.shoebox.dto.ProductImageDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ProductImage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productimage_seq_generator")
    @SequenceGenerator(name = "productimage_seq_generator", sequenceName = "productimage_seq", allocationSize = 1)
    @Column(name = "productimage_id")
    private Long productImageId;

    @Column(name = "file_name", nullable = false, length = 200)
    private String fileName;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public ProductImageDTO toDTO() {
        return ProductImageDTO.builder()
                .productImageId(this.productImageId)
                .fileName(this.fileName)
                .sortOrder(this.sortOrder)
                .productId(this.product.getProductId())
                .build();
    }
}
