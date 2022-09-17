package com.studyLog4u.service;

import com.studyLog4u.dto.ReviewDto;
import com.studyLog4u.entity.Review;
import com.studyLog4u.entity.Study;

import java.util.List;

public interface ReviewService {

    Long register(ReviewDto dto);
    List<ReviewDto> getList(Long studyId);
    void delete(Long id);
    void update(ReviewDto dto);

    /* DTO to Entity */
    default Review dtoToEntity(ReviewDto dto){
        Study study = Study.builder().id(dto.getStudyId()).build();

        Review entity = Review.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .study(study)
                .build();
        return entity;
    }

    /* Entity to DTO */
    default ReviewDto entityToDto(Review entity){
        ReviewDto dto = ReviewDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}
