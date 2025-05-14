package com.test.shoebox.entity;

import com.test.shoebox.dto.ProductGroupDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ProductGroup")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productgroup_seq_generator")
    @SequenceGenerator(name = "productgroup_seq_generator", sequenceName = "productgroup_seq", allocationSize = 1)
    @Column(name = "productgroup_id")
    private Long productGroupId;

    public ProductGroupDTO toDTO() {
        return ProductGroupDTO.builder()
                .productGroupId(this.productGroupId)
                .build();
    }
}