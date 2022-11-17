package com.studyLog4u.service;

import com.studyLog4u.dto.PageRequestDto;
import com.studyLog4u.dto.PageResultDto;
import com.studyLog4u.dto.StudyDto;
import com.studyLog4u.entity.Study;

import java.time.LocalDateTime;
import java.util.List;

public interface StudyService {
    Long register(StudyDto dto);
    PageResultDto<StudyDto, Study> getList(PageRequestDto dto);
    StudyDto get(Long id);
    void delete(Long id);
    void update(StudyDto dto);

    List<Study> getTodayStudyList();

    /* DTO to Entity */
    default Study dtoToEntity(StudyDto dto){
        Study entity = Study.builder()
                    .id(dto.getId())
                    .category(dto.getCategory())
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .notiDate(dto.getNotiDate())
                    .nickname(dto.getNickname())
                    .userId(dto.getUserId())
                    .build();
        return entity;
    }

    /* Entity to DTO */
    default StudyDto entityToDto(Study entity){
        StudyDto dto = StudyDto.builder()
                    .id(entity.getId())
                    .category(entity.getCategory())
                    .title(entity.getTitle())
                    .content(entity.getContent())
                    .regDate(entity.getRegDate())
                    .modDate(entity.getModDate())
                    .notiDate(entity.getNotiDate())
                    .nickname(entity.getNickname())
                    .userId(entity.getUserId())
                    .build();
        return dto;
    }
}
