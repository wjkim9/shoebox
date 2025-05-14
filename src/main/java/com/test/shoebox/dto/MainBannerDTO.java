package com.test.shoebox.dto;

import com.test.shoebox.entity.MainBanner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainBannerDTO {
	private Long mainBannerId;

    private String fileName;

    private Integer sortOrder;

    public MainBanner toEntity() {
        return MainBanner.builder()
                .mainBannerId(this.mainBannerId)
                .fileName(this.fileName)
                .sortOrder(this.sortOrder)
                .build();
    }
}
