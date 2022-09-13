package com.studyLog4u.dto;

import com.studyLog4u.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudyDto {
    private Long id;

    @Schema(description = "카테고리", example = "알고리즘", required = true)
    @NotBlank
    private String category;

    @Schema(description = "제목", example = "제목입니다.", required = true)
    @NotBlank
    private String title;

    @Schema(description = "내용", example = "내용입니다.", required = true)
    @NotBlank
    private String content;

    private String userId;

    private String nickname;

    private LocalDateTime regDate, modDate, notiDate;
}
