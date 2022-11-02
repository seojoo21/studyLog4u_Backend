package com.studyLog4u.repository;

import com.studyLog4u.entity.FileMng;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface FileMngRepository extends JpaRepository<FileMng, Long>, QuerydslPredicateExecutor<FileMng> {

    @Transactional
    @Modifying
    @Query("delete from FileMng fm where fm.boardId = :studyId")
    void deleteByStudyBoardId(@Param("studyId")Long studyId);

    List<FileMng> findAllByModDate(LocalDateTime date);
}
