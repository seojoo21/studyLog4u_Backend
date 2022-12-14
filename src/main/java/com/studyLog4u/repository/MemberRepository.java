package com.studyLog4u.repository;

import com.studyLog4u.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findMemberByUserId(String userId);

}
