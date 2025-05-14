package com.test.shoebox.entity;

import com.test.shoebox.dto.FavoriteBrandDTO;

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
@Table(name = "FavoriteBrand")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favoritebrand_seq_generator")
    @SequenceGenerator(name = "favoritebrand_seq_generator", sequenceName = "favoritebrand_seq", allocationSize = 1)
    @Column(name = "favoritebrand_id")
    private Long favoriteBrandId;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "members_id", nullable = false)
    private Members members;

    public FavoriteBrandDTO toDTO() {
        return FavoriteBrandDTO.builder()
                .favoriteBrandId(this.favoriteBrandId)
                .brandId(this.brand.getBrandId())
                .membersId(this.members.getMembersId())
                .build();
    }
}
