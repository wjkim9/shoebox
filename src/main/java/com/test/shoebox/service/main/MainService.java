package com.test.shoebox.service.main;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.shoebox.entity.EventPost;
import com.test.shoebox.repository.CustomRepository;
import com.test.shoebox.repository.EventPostRepository;
import com.test.shoebox.repository.ProductImageRepository;
import com.test.shoebox.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainService {
	
	private final EventPostRepository eventPostRepository;
	
	//메인화면 큐레이션
	public List<EventPost> getCurationList() {
		
		List<EventPost> eventPostList = eventPostRepository.findAllByOrderByWriteDateDesc();
		
		return eventPostList;
		
	}
}
