package com.studyLog4u.controller;

import com.studyLog4u.common.ApiDataResponse;
import com.studyLog4u.common.ApiDocumentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Tag(name="File Controller", description = "파일 업로드 관련 컨트롤러")
@RestController
@RequestMapping("/api/file")
@Slf4j
public class FileController {

    @ApiDocumentResponse
    @Operation(summary = "이미지 업로드 설정", description = "base64로 변환되는 이미지 파일의 업로드 방식을 변경해주는 API입니다. ")
    @PostMapping(value="/imgUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiDataResponse<Map> uploadFile(@RequestParam("image") MultipartFile multipartFile){
        Map<String, String> resultMap = new HashMap<>();

        try {
            String fileOriginalName = multipartFile.getOriginalFilename();

            System.out.println("========check============");
            System.out.println(fileOriginalName);

        } catch (Exception e) {
            log.error("Error :: " + e);
            resultMap.put("Error", "error");
        }

        return new ApiDataResponse<>(resultMap);
    }
}
