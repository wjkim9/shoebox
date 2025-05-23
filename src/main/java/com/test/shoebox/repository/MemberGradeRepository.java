package com.test.shoebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.shoebox.entity.MemberGrade;

import java.util.List;
import java.util.Optional;

public interface MemberGradeRepository extends JpaRepository<MemberGrade, Long>{

    Optional<MemberGrade> findByMembers_MembersId(Long membersMembersId);
    List<MemberGrade> findAllByMembers_MembersId(Long membersMembersId);
}
