package com.studyLog4u.common;

import com.studyLog4u.common.ApiDataResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "API 호출 성공",
                content = @Content(schema = @Schema(implementation = ApiDataResponse.class))),

        @ApiResponse(
                responseCode = "500",
                description = "서버 에러",
                content = @Content(schema = @Schema(implementation = ApiDataResponse.class)))

})
public @interface ApiDocumentResponse {
}