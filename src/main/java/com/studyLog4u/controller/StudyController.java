package com.studyLog4u.controller;

import com.studyLog4u.common.ApiDataResponse;
import com.studyLog4u.common.ApiDocumentResponse;
import com.studyLog4u.dto.PageRequestDto;
import com.studyLog4u.dto.PageResultDto;
import com.studyLog4u.dto.StudyDto;
import com.studyLog4u.entity.Study;
import com.studyLog4u.service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Tag(name="Study Controller", description = "스터디 등록 관련 컨트롤러")
@RestController
@RequestMapping("/api/study")
@Slf4j
@RequiredArgsConstructor
public class StudyController {

    private final StudyService service;

    /** 전체 스터디 목록 조회
     * @param pageRequestDto 페이지 처리 요청 DTO
     * @return
     */
    @ApiDocumentResponse
    @Operation(summary = "전체 스터디 목록 조회", description = "전체 스터디 목록 조회 API 입니다.\n\n **검색조건(type)>> 카테고리:g, 내용:c, 제목:t**")
    @GetMapping(value="/getList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiDataResponse<PageResultDto<StudyDto, Study>> getList(PageRequestDto pageRequestDto){
        log.info("StudyController 전체 스터디 목록 조회");
        return new ApiDataResponse<>(service.getList(pageRequestDto));
    }

    /** 신규 스터디 등록
     * @param studyDto 스터디 Dto.
     * @return
     */
    @ApiDocumentResponse
    @Operation(summary = "신규 스터디 등록", description = "신규 스터디 등록 API 입니다. \n\n **id, regDate, modDate는 서버에서 자동 생성됩니다.**")
    @PostMapping(value="/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiDataResponse<String> register(@RequestBody @Valid StudyDto studyDto) {
        log.info("StudyController 신규 스터디 등록");
        Long id = service.register(studyDto);
        String message = "스터디 등록 성공 : " + id;
        return new ApiDataResponse<>(message);
    }

    /** 스터디 조회
     * @param id 게시물 id
     * @return
     */
    @ApiDocumentResponse
    @Operation(summary = "스터디 조회", description = "스터디 조회 API 입니다.")
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiDataResponse<StudyDto> get(@PathVariable("id") Long id, PageRequestDto pageRequestDto){
        log.info("StudyController 스터디 조회 id: " + id);
        return new ApiDataResponse<>(service.get(id));
    }

    /** 스터디 삭제
     * @param id 게시물 id
     * @return
     */
    @ApiDocumentResponse
    @Operation(summary = "스터디 삭제", description = "스터디 삭제 API 입니다.")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiDataResponse<String> delete(@PathVariable("id") Long id){
        log.info("StudyController 스터디 삭제 id: " + id);
        service.delete(id);
        String message = "삭제 성공 id: "+ id;
        return new ApiDataResponse<>(message);
    }

    /** 스터디 수정
     * @param studyDto 스터디 Dto. id, category, title, content 필수
     * request body:
     * {
     *     "id": 20220725223900465,
     *     "category": "카테고리",
     *     "title": "제목",
     *     "content": "내용"
     * }
     * @return
     */
    @ApiDocumentResponse
    @Operation(summary = "스터디 수정", description = "스터디 수정 API 입니다. \n\n **RequestBody에 id 전달 필수. regDate, modDate는 서버에서 자동 생성됩니다.**")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiDataResponse<String> update(@PathVariable("id") Long id, @RequestBody @Valid StudyDto studyDto, PageRequestDto pageRequestDto){
        log.info("StudyController 스터디 수정");
        service.update(studyDto);
        String message = "스터디 수정 성공: " + id;
        return new ApiDataResponse<>(message);
    }

    @ApiDocumentResponse
    @Operation(summary = "오늘 복습할 스터디 리스트 조회", description = "오늘 복습할 스터디 리스트를 조회하는 API 입니다.")
    @GetMapping(value="/getTodayStudyList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiDataResponse<List<Study>> getTodayStudyList(){
        log.info("StudyController 오늘 복습할 스터디 리스트 조회");
        return new ApiDataResponse<>(service.getTodayStudyList());
    }
}
