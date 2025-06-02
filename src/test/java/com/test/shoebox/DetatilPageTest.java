package com.test.shoebox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.shoebox.dto.DetailMap;
import com.test.shoebox.dto.ProductImageDTO;
import com.test.shoebox.entity.Product;
import com.test.shoebox.entity.ProductImage;
import com.test.shoebox.mapper.ProductPostMapper;
import com.test.shoebox.repository.ProductImageRepository;
import com.test.shoebox.repository.ProductRepository;

import lombok.Getter;
import lombok.Setter;

@SpringBootTest
@Getter
@Setter
public class DetatilPageTest {
	
	@Autowired
	private ProductPostMapper productPostMapper;
	
	@Autowired
	private ProductImageRepository productImageRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void time() {
		System.out.println("지금이 몇 시 더냐~? : " + productPostMapper.time());
	}
	
	@Test
	public void detailtest() {

		List<DetailMap> list = productPostMapper.detailtest("1");

		for (DetailMap dto : list) {
			System.out.println(dto);
		}

	}
	
	@Test
	public void productImageList() {
		
		Optional<Product> product = productRepository.findById(1L);
		
		List<ProductImage> productImageList = productImageRepository.findByProductOrderBySortOrderAsc(product.get());
		
		List<ProductImageDTO> productImageDTOList = new ArrayList<>();

		for (ProductImage pi : productImageList) {
			//System.out.println(pi.getFileName());

			ProductImageDTO dto = pi.toDTO();

			System.out.println(dto.getFileName());
			
			//productImageDTOList.add(dto);
		}
		
	}
	
}
