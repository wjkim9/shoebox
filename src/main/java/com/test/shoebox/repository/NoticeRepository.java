package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>{

}
