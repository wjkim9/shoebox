package com.test.shoebox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.shoebox.entity.CartItem;
import com.test.shoebox.entity.Members;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    List<CartItem> findByMembersMembersId(Long membersId);

    @Query("SELECT ci FROM CartItem ci " +
       "JOIN FETCH ci.productStock ps " +
       "JOIN FETCH ps.product p " +
       "LEFT JOIN FETCH p.productImages " +
       "WHERE ci.members.membersId = :membersId")
    List<CartItem> findByMembersIdWithProductAndImages(@Param("membersId") Long membersId);

    List<CartItem> findByMembers(Members members);

    List<CartItem> findByMembers_MembersId(Long memberId);

}