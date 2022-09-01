package com.studyLog4u.repository;

import com.studyLog4u.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findMemberByUserId(String userId);
    List<Member> findByUserId(String userId);

}
