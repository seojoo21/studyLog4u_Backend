package com.studyLog4u.service.Impl;

import com.studyLog4u.dto.PageResultDto;
import com.studyLog4u.dto.ReviewDto;
import com.studyLog4u.entity.Review;
import com.studyLog4u.entity.Study;
import com.studyLog4u.repository.ReviewRepository;
import com.studyLog4u.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public Long register(ReviewDto dto) {
        // pk id 값 설정
        Long id = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        dto.setId(id);

        Review entity = dtoToEntity(dto);
        reviewRepository.save(entity);
        log.info("ReviewServiceImpl: register...");
        return entity.getId();
    }

    @Override
    public List<ReviewDto> getList(Long studyId) {
        List<Review> result = reviewRepository.getReviewsByStudyOrderByIdDesc(Study.builder().id(studyId).build());
        log.info("ReviewServiceImpl: getList...");
        return result.stream().map(review -> entityToDto(review)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        reviewRepository.deleteById(id);
        log.info("ReviewServiceImpl: delete...");
    }

    @Override
    public void update(ReviewDto dto) {
        Review entity = dtoToEntity(dto);
        reviewRepository.save(entity);
        log.info("ReviewServiceImpl: update...");
    }
}
