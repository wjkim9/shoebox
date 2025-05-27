package com.test.shoebox.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.shoebox.entity.CartItem;
import com.test.shoebox.entity.Members;
import com.test.shoebox.entity.ProductStock;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    Optional<CartItem> findByMembersAndProductStock(Members members, ProductStock productStock);

    List<CartItem> findByMembersMembersId(Long membersId);

    @Query("SELECT ci FROM CartItem ci " +
       "JOIN FETCH ci.productStock ps " +
       "JOIN FETCH ps.product p " +
       "JOIN FETCH p.productImages pi " +
       "WHERE ci.members.membersId = :membersId AND pi.sortOrder = 1")
    List<CartItem> findByMembersIdWithProductAndImages(@Param("membersId") Long membersId);

    List<CartItem> findByMembers(Members members);

    List<CartItem> findByMembers_MembersId(Long memberId);

}
