package com.studyLog4u.repository;

import com.studyLog4u.entity.FileMng;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FileMngRepository extends JpaRepository<FileMng, Long>, QuerydslPredicateExecutor<FileMng> {
}
