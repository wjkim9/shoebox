package com.test.shoebox.entity;

import com.test.shoebox.dto.ProductStockDTO;

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
@Table(name = "ProductStock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productstock_seq_generator")
    @SequenceGenerator(name = "productstock_seq_generator", sequenceName = "productstock_seq", allocationSize = 1)
    @Column(name = "productstock_id")
    private Long productStockId;

    @Column(name = "shoe_size", nullable = false, length = 20)
    private String shoeSize;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public ProductStockDTO toDTO() {
        return ProductStockDTO.builder()
                .productStockId(this.productStockId)
                .shoeSize(this.shoeSize)
                .stockQuantity(this.stockQuantity)
                .productId(this.product.getProductId())
                .build();
    }
}