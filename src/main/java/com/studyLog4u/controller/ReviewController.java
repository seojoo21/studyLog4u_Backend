package com.studyLog4u.controller;

import com.studyLog4u.common.ApiDataResponse;
import com.studyLog4u.utils.ApiDocumentResponse;
import com.studyLog4u.dto.ReviewDto;
import com.studyLog4u.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name="Review Controller", description = "리뷰 등록 관련 컨트롤러")
@RestController
@RequestMapping("/api/review")
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    /** 리뷰 목록 조회
     * @param studyId studyId (study_board PK id)
     * @return
     */
    @ApiDocumentResponse
    @Operation(summary = "특정 스터디의 전체 리뷰 목록 조회", description = "전체 리뷰 목록 조회 API 입니다.\n\n")
    @GetMapping(value = "/getList/{studyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiDataResponse<List<ReviewDto>> getList(@PathVariable("studyId") Long studyId){
        log.info("ReviewController 전체 리뷰 목록 조회");
        return new ApiDataResponse<>(service.getList(studyId));
    }

    /** 신규 리뷰 등록
     * @param reviewDto 리뷰Dto.
     * request body:
     * {
     *     "studyId": 20220723135858990,
     *     "content": "신규 리뷰 등록 "
     * }
     * @return
     */
    @ApiDocumentResponse
    @Operation(summary = "리뷰 등록", description = "리뷰 등록 API 입니다.\n\n **RequestBody에 studyId 전달 필수. regDate, modDate는 서버에서 자동 생성됩니다.**")
    @PostMapping(value="/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiDataResponse<String> register(@RequestBody @Valid ReviewDto reviewDto){
        log.info("ReviewController 리뷰 등록");
        Long id = service.register(reviewDto);
        String message = "리뷰 등록 성공: " + id;
        return new ApiDataResponse<>(message);
    }

    /** 리뷰 삭제
     * @param id
     * @return
     */
    @ApiDocumentResponse
    @Operation(summary = "리뷰 삭제", description = "리뷰 삭제 API 입니다.")
    @DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiDataResponse<String> delete(@PathVariable("id") Long id){
        log.info("ReviewController 리뷰 삭제");
        service.delete(id);
        String message = "삭제 성공 id: "+ id;
        return new ApiDataResponse<>(message);
    }

    /** 리뷰 수정
     * @param reviewDto 리뷰 Dto.
     * request body:
     * {
     *     "id": 20220725223900465,
     *     "studyId": 20220723135858990,
     *     "content": "리뷰 수정22222"
     * }
     * @return
     */
    @ApiDocumentResponse
    @Operation(summary = "리뷰 수정", description = "리뷰 수정 API 입니다. \n\n **RequestBody에 id, studyId 전달 필수. regDate, modDate는 서버에서 자동 생성됩니다.**")
    @PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiDataResponse<String> update(@RequestBody @Valid ReviewDto reviewDto){
        log.info("ReviewController 리뷰 수정");
        service.update(reviewDto);
        String message = "리뷰 수정 성공: " + reviewDto.getId();
        return new ApiDataResponse<>(message);
    }
}
