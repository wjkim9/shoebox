package com.test.shoebox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.test.shoebox.repository")
@EntityScan(basePackages = "com.test.shoebox.entity")
public class ShoeboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoeboxApplication.class, args);
	}

}
