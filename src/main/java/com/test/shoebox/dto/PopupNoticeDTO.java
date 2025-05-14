package com.test.shoebox.dto;


import com.test.shoebox.entity.PopupNotice;

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
public class PopupNoticeDTO {
	private Long popupNoticeId;

    private String fileName;

    public PopupNotice toEntity() {
        return PopupNotice.builder()
                .popupNoticeId(this.popupNoticeId)
                .fileName(this.fileName)
                .build();
    }
}
