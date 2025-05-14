package com.test.shoebox.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.VisitorCount;

public interface VisitorCountRepository extends JpaRepository<VisitorCount, LocalDate> {

}
