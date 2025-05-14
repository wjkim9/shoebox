package com.test.shoebox.entity;

import com.test.shoebox.dto.MainBannerDTO;

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
@Table(name = "MainBanner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mainbanner_seq_generator")
    @SequenceGenerator(name = "mainbanner_seq_generator", sequenceName = "mainbanner_seq", allocationSize = 1)
    @Column(name = "mainbanner_id")
    private Long mainBannerId;

    @Column(name = "file_name", nullable = false, length = 200)
    private String fileName;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    public MainBannerDTO toDTO() {
        return MainBannerDTO.builder()
                .mainBannerId(this.mainBannerId)
                .fileName(this.fileName)
                .sortOrder(this.sortOrder)
                .build();
    }
}
