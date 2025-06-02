package com.test.shoebox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.EventPost;

public interface EventPostRepository extends JpaRepository<EventPost, Long>{

	List<EventPost> findAllByOrderByWriteDateDesc();

}
