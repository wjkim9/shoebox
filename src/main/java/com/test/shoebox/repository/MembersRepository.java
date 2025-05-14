package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Members;

public interface MembersRepository extends JpaRepository<Members, Long>{

}
