package com.test.shoebox.entity;

import com.test.shoebox.dto.ProductPostImageDTO;

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
@Table(name = "ProductPostImage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productpostimage_seq_generator")
    @SequenceGenerator(name = "productpostimage_seq_generator", sequenceName = "productpostimage_seq", allocationSize = 1)
    @Column(name = "productpostimage_id")
    private Long productPostImageId;

    @Column(name = "file_name", nullable = false, length = 200)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "productpost_id", nullable = false)
    private ProductPost productPost;

    public ProductPostImageDTO toDTO() {
        return ProductPostImageDTO.builder()
                .productPostImageId(this.productPostImageId)
                .fileName(this.fileName)
                .productPostId(this.productPost.getProductPostId())
                .build();
    }
}