package com.studyLog4u.common;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

public class ApiDataResponse<T> {
    @Schema(description = "응답코드", example = "OK/INTERNAL_SERVER_ERROR")
    public HttpStatus status;

    @Schema(description = "데이터", example = "data")
    public Object data;

    public ApiDataResponse(Object data) {
        this.status = data == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK;
        this.data = data;
    }

}
