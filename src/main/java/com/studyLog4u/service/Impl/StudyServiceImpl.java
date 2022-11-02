package com.studyLog4u.service.Impl;

import com.querydsl.core.BooleanBuilder;
import com.studyLog4u.dto.FileMngDto;
import com.studyLog4u.dto.PageRequestDto;
import com.studyLog4u.dto.PageResultDto;
import com.studyLog4u.dto.StudyDto;
import com.studyLog4u.entity.FileMng;
import com.studyLog4u.entity.QStudy;
import com.studyLog4u.entity.Study;
import com.studyLog4u.repository.FileMngRepository;
import com.studyLog4u.repository.ReviewRepository;
import com.studyLog4u.repository.StudyRepository;
import com.studyLog4u.service.StudyService;
import com.studyLog4u.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepository;
    private final ReviewRepository reviewRepository;
    private final FileMngRepository fileMngRepository;

    @Override
    public Long register(StudyDto dto){
        // pk id 값 설정
        Long id = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        dto.setId(id);

        // 신규 스터디 등록
        Study entity = dtoToEntity(dto);
        studyRepository.save(entity);
        log.info("StudyServiceImpl: Register...");

        String content = dto.getContent();
        List<FileMng> files = CommonUtil.getUploadedFileList(content, "study_board", id);

        if(!ObjectUtils.isEmpty(files)){
            fileMngRepository.saveAll(files);
            log.info("StudyServiceImpl: Register Uploaded File Data...");
        }

        return entity.getId();
    }

    @Override
    public PageResultDto<StudyDto, Study> getList(PageRequestDto requestDto){
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDto);
        Page<Study> result = studyRepository.findAll(booleanBuilder, pageable);
        Function<Study, StudyDto> fn = (entity -> entityToDto(entity));
        log.info("StudyServiceImpl: getList...");
        return new PageResultDto<>(result, fn);
    }

    @Override
    public StudyDto get(Long id) {
        Optional<Study> result = studyRepository.findById(id);
        log.info("StudyServiceImpl: get...");
        return result.isPresent()? entityToDto(result.get()) : null;
    }

    @Override
    public void delete(Long id) {
        reviewRepository.deleteByStudyId(id); // 스터디 삭제 시 해당 스터디의 리뷰도 함께 삭제한다. (FK로 연결되어 있으므로 자식 테이블에서 먼저 삭제)
        fileMngRepository.deleteByStudyBoardId(id); // 스터디 삭제 시 해당 스터디의 파일 데이터도 함께 삭제한다.
        studyRepository.deleteById(id);
        log.info("StudyServiceImpl: Delete...");
    }

    @Override
    public void update(StudyDto dto) {
        Optional<Study> result = studyRepository.findById(dto.getId());

        if(result.isPresent()){
            Study entity = result.get();

            entity.changeCategory(dto.getCategory());
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            entity.changeNotiDate(dto.getNotiDate());

            studyRepository.save(entity);
            log.info("StudyServiceImpl: Update...");

            String content = dto.getContent();
            List<FileMng> contentFiles = CommonUtil.getUploadedFileList(content, "study_board", dto.getId());
            fileMngRepository.deleteByStudyBoardId(dto.getId());
            fileMngRepository.saveAll(contentFiles);
            log.info("StudyServiceImpl: Update Uploaded File Data...");

        }
    }

    // 검색 처리 메서드 (Querydsl 처리)
    private BooleanBuilder getSearch(PageRequestDto requestDto) {
        String type = requestDto.getType();
        String keyword = requestDto.getKeyword();
        QStudy qStudy = QStudy.study;

        // 0. 검색 조건이 없는 경우
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(type == null || type.trim().length() == 0){
            return booleanBuilder;
        }

        // 1. 검색 조건이 있는 경우
        // 검색 조건 생성
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("g")){
            conditionBuilder.or(qStudy.category.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qStudy.content.contains(keyword));
        }
        if(type.contains("t")){
            conditionBuilder.or(qStudy.title.contains(keyword));
        }

        // 모든 검색 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
