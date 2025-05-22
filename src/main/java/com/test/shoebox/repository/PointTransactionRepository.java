package com.test.shoebox.repository;

import com.test.shoebox.entity.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {
    
	@Query(value = "SELECT pointtransaction_id, transaction_type, transaction_point, " +
		       "reason, transaction_datetime, members_id " +
		       "FROM POINTTRANSACTION " +  // 대문자로 테이블명 수정
		       "WHERE members_id = :membersId " +
		       "ORDER BY transaction_datetime DESC", 
		       nativeQuery = true)
		List<Object[]> findTransactionsByMembersId(@Param("membersId") Long membersId);

}
