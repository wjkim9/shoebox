package com.test.shoebox.service.main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.test.shoebox.dto.BrandDTO;
import com.test.shoebox.dto.CategoriesDTO;
import com.test.shoebox.entity.Brand;
import com.test.shoebox.entity.Categories;
import com.test.shoebox.entity.EventPost;
import com.test.shoebox.repository.BrandRepository;
import com.test.shoebox.repository.CategoriesRepository;
import com.test.shoebox.repository.CustomRepository;
import com.test.shoebox.repository.EventPostRepository;
import com.test.shoebox.repository.ProductImageRepository;
import com.test.shoebox.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainService {
	
	private final EventPostRepository eventPostRepository;
	private final BrandRepository brandRepository;
	private final CategoriesRepository categoriesRepository;
	
	//메인화면 큐레이션
	public List<EventPost> getCurationList() {
		
		List<EventPost> eventPostList = eventPostRepository.findAllByOrderByWriteDateDesc();
		
		return eventPostList;
		
	}
	
	
	//필터 - 브랜드
	public List<BrandDTO> getBrandOnFilter() {
		
		List<Brand> brandList = brandRepository.findAllByOrderByBrandNameAsc();
		
		List<BrandDTO> brandDtoList = new ArrayList<>();
		
		for(Brand brand : brandList) {
			
			brandDtoList.add(brand.toDTO());
		}
		
		return brandDtoList;
	}
	
	//필터 - 카테고리
	public List<CategoriesDTO> getCategoriesOnFilter() {
		
		List<Categories> categoriesList = categoriesRepository.findAllByOrderByCategoriesIdAsc();
		
		List<CategoriesDTO> categoriesDtoList = new ArrayList<>();
		
		for(Categories Categories : categoriesList) {
			
			categoriesDtoList.add(Categories.toDTO());
		}
		
		return categoriesDtoList;
	}
	
}
