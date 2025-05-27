package com.test.shoebox.repository;

import com.test.shoebox.entity.MemberGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.MemberGrade;

import java.util.List;
import java.util.Optional;

public interface MemberGradeRepository extends JpaRepository<MemberGrade, Long>{
    List<MemberGrade> findAllByMembers_MembersId(Long membersId);
    Optional<MemberGrade> findByMembers_MembersId(Long membersId);
}
