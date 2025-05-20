package com.test.shoebox;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.shoebox.dto.DetailMap;
import com.test.shoebox.mapper.ProductPostMapper;

import lombok.Getter;
import lombok.Setter;

@SpringBootTest
@Getter
@Setter
public class DetatilPageTest {
	
	@Autowired
	private ProductPostMapper productPostMapper;
	
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
	
}
