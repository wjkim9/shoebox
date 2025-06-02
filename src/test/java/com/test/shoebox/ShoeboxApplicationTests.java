package com.test.shoebox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootTest
class ShoeboxApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	void DatabaseConnectionTest() throws Exception {
		try (Connection conn = dataSource.getConnection()) {
			System.out.println("연결 성공! DB URL: " + conn.getMetaData().getURL());
			Assertions.assertNotNull(conn);
		}
	}


}
