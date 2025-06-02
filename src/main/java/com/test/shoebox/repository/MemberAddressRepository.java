package com.test.shoebox.repository;

import com.test.shoebox.dto.MemberAddressDTO;

import com.test.shoebox.entity.MemberAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long> {
    
	@Query(value = "SELECT memberaddress_id, zip_code, road_address, jibun_address, " +
		       "detail_address, address_reference, members_id " +
		       "FROM MEMBERADDRESS " +
		       "WHERE members_id = :membersId", 
		       nativeQuery = true)
		List<Object[]> findAddressesByMembersId(@Param("membersId") Long membersId);




}
