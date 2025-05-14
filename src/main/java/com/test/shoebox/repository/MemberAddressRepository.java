package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.MemberAddress;

public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long>{

}
