package com.studyLog4u.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileMngDto {
    private Long fileSeq;
    private String boardName;
    private Long boardId;
    private String fileType;
    private String path;
}

