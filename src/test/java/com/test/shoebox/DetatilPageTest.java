package com.test.shoebox;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
		
		List<Map<String, String>> result = productPostMapper.detailtest();
		
		System.out.println(result.get(0));
	}
	
}
