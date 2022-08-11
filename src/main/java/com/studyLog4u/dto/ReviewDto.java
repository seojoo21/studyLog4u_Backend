package com.studyLog4u.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDto {
    private Long id;
    private Long studyId;
    private String content;
    private LocalDateTime regDate, modDate;
}
