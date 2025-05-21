package com.test.shoebox.repository;

import com.test.shoebox.dto.MemberWithGradeDTO;
import com.test.shoebox.entity.Members;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MembersRepository extends JpaRepository<Members, Long> {

    @Query(value = """
        SELECT new com.test.shoebox.dto.MemberWithGradeDTO(
            m.membersId, m.accountName, m.name, m.nickname, m.email, m.contact, 
            m.footSize, m.point, g.gradeName, m.isDeleted, m.joinDatetime) 
        FROM Members m 
        LEFT JOIN MemberGrade g ON m.membersId = g.members.membersId 
        WHERE 
            (:keyword IS NULL OR 
            m.name LIKE CONCAT('%', :keyword, '%') OR 
            m.accountName LIKE CONCAT('%', :keyword, '%') OR 
            m.contact LIKE CONCAT('%', :keyword, '%')) 
        AND 
            (:isDeleted IS NULL OR m.isDeleted = :isDeleted)
        """,
        countQuery = """
        SELECT COUNT(m) 
        FROM Members m 
        WHERE 
            (:keyword IS NULL OR 
            m.name LIKE CONCAT('%', :keyword, '%') OR 
            m.accountName LIKE CONCAT('%', :keyword, '%') OR 
            m.contact LIKE CONCAT('%', :keyword, '%')) 
        AND 
            (:isDeleted IS NULL OR m.isDeleted = :isDeleted)
        """)
    Page<MemberWithGradeDTO> findMembersWithGrade(
            @Param("keyword") String keyword,
            @Param("isDeleted") Integer isDeleted,
            Pageable pageable);
    
    
    @Query("""
            SELECT new com.test.shoebox.dto.MemberWithGradeDTO(
                m.membersId, m.accountName, m.name, m.nickname, m.email, m.contact,
                m.footSize, m.point, g.gradeName, m.isDeleted, m.joinDatetime
            )
            FROM Members m
            LEFT JOIN MemberGrade g ON m = g.members
            WHERE m.membersId = :membersId
        """)
    Optional<MemberWithGradeDTO> findMemberWithGradeById(@Param("membersId") Long membersId);
}
