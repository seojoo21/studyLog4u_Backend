package com.studyLog4u.repository;

import com.studyLog4u.entity.Review;
import com.studyLog4u.entity.Study;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, QuerydslPredicateExecutor<Review> {

    @Transactional
    @Modifying
    @Query("delete from Review r where r.study.id = :studyId")
    void deleteByStudyId(@Param("studyId") Long studyId);
    List<Review> getReviewsByStudyOrderByIdDesc(Study study);

}
