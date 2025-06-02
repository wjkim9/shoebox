package com.test.shoebox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailMap {
    
    private String fileName;         // ProductImage.file_name
    private Long productpostId;      // ProductPost.productpost_id

}
