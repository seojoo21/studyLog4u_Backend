package com.studyLog4u.service.Impl;

import com.querydsl.core.BooleanBuilder;
import com.studyLog4u.dto.PageRequestDto;
import com.studyLog4u.dto.PageResultDto;
import com.studyLog4u.dto.StudyDto;
import com.studyLog4u.entity.QStudy;
import com.studyLog4u.entity.Study;
import com.studyLog4u.repository.FileMngRepository;
import com.studyLog4u.repository.ReviewRepository;
import com.studyLog4u.repository.StudyRepository;
import com.studyLog4u.service.StudyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

        Parser parser = Parser.builder().build();
        Node markDownDocument = parser.parse(dto.getContent());
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String renderedContents = renderer.render(markDownDocument);

        Element htmlDocument = Jsoup.parse(renderedContents);
        Elements imgTags = htmlDocument.getElementsByTag("img");

        List<String> imgUrls = new ArrayList<>();
        if(!ObjectUtils.isEmpty(imgTags)){
            for(Element imgTag : imgTags){
                String imgUrl = imgTag.attr("src");
                imgUrls.add(imgUrl);
            }
        }

        log.info("StudyServiceImpl: register...");

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
