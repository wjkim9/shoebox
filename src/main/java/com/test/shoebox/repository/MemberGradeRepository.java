package com.test.shoebox.repository;

import com.test.shoebox.entity.MemberGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberGradeRepository extends JpaRepository<MemberGrade, Long> {
    Optional<MemberGrade> findByMembers_MembersId(Long membersId);
}
