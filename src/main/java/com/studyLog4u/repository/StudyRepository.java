package com.studyLog4u.repository;

import com.studyLog4u.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;
import java.util.List;


public interface StudyRepository extends JpaRepository<Study, Long>, QuerydslPredicateExecutor<Study> {

    List<Study> findAllByNotiDate(LocalDateTime date);
}
