package com.studyLog4u.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDto {

    @Schema(description = "현재 페이지", example = "1")
    @NotBlank
    private int page;
    @Schema(description = "한 페이지당 게시물 수", example = "10")
    @NotBlank
    private int size;

    @Schema(description = "검색 조건", example = "g")
    private String type;

    @Schema(description = "검색어", example = "백준")
    private String keyword;

    public PageRequestDto(){
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1, size, sort);
    }
}
