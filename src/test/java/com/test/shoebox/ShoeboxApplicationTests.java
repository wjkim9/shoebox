package com.test.shoebox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShoeboxApplicationTests {
   
   @Autowired
   private DataSource dataSource;
   
   @Test
   public void testDataSource() throws Exception {
      
      assertNotNull(dataSource);
      
      Connection conn = dataSource.getConnection();
      
      assertEquals(false, conn.isClosed());
      
   }
}
