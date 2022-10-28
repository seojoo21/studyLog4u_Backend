package com.studyLog4u.dto;

import com.studyLog4u.utils.MultipartUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileDetailDto {
    private String id; // UUID로 생성한 파일 고유의 id
    private String name; // 파일 업로드 시점의 파일명
    private String format; // 파일 확장자명
    private String path; // 파일의 실제 물리적 경로
    private long bytes; // 파일 크기
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public static FileDetailDto multipartOf(MultipartFile multipartFile){
        final String fileId = MultipartUtil.createFileId();
        final String format = MultipartUtil.getFormat(multipartFile.getContentType());
        return FileDetailDto.builder()
                .id(fileId)
                .name(multipartFile.getOriginalFilename())
                .format(format)
                .path(MultipartUtil.createPath(fileId,format))
                .bytes(multipartFile.getSize())
                .build();
    }
}
