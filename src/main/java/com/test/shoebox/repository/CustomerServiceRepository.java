package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.CustomerService;

public interface CustomerServiceRepository extends JpaRepository<CustomerService, Long>{

}
