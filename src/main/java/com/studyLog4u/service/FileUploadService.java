package com.studyLog4u.service;

import com.studyLog4u.dto.FileDetailDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    FileDetailDto save(MultipartFile multipartFile);
}
