package com.test.shoebox.dto;

import java.time.LocalDate;

import com.test.shoebox.entity.VisitorCount;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class VisitorCountDTO {
	private LocalDate visitDate;
	
	public VisitorCount toEntity() {
        return VisitorCount.builder()
                .visitDate(this.visitDate)
                .build();
    }

}
