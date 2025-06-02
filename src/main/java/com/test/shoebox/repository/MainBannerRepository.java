package com.test.shoebox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.MainBanner;

public interface MainBannerRepository extends JpaRepository<MainBanner, Long>{

	List<MainBanner> findAllByOrderBySortOrder();

}
