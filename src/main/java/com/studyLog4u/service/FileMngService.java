package com.studyLog4u.service;

import com.studyLog4u.dto.FileMngDto;
import com.studyLog4u.entity.FileMng;

import java.util.List;

public interface FileMngService {

    Long register(List<FileMngDto> fileList);
    void delete(String path);

    /* DTO to Entity */
    default FileMng dtoToEntity(FileMngDto dto) {
        FileMng entity = FileMng.builder()
                .fileSeq(dto.getFileSeq())
                .boardName(dto.getBoardName())
                .boardId(dto.getBoardId())
                .fileType(dto.getFileType())
                .path(dto.getPath())
                .build();
        return entity;
    }

    /* Entity to DTO*/
    default FileMngDto entityToDto(FileMng entity){
        FileMngDto dto = FileMngDto.builder()
                .fileSeq(entity.getFileSeq())
                .boardName(entity.getBoardName())
                .boardId(entity.getBoardId())
                .fileType(entity.getFileType())
                .path(entity.getPath())
                .build();
        return dto;
    }
}
