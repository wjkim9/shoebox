package com.test.shoebox.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.Members;

public interface MembersRepository extends JpaRepository<Members, Long>{

	Optional<Members> findByEmail(String email);

}
