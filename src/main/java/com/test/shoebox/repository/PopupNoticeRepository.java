package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.PopupNotice;

public interface PopupNoticeRepository extends JpaRepository<PopupNotice, Long> {

}
