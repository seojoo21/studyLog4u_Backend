package com.studyLog4u.service.Impl;

import com.studyLog4u.aws.AmazonS3ResourceStorage;
import com.studyLog4u.dto.FileDetailDto;
import com.studyLog4u.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    @Override
    public FileDetailDto save(MultipartFile multipartFile) {
        FileDetailDto fileDetailDto = FileDetailDto.multipartOf(multipartFile);
        amazonS3ResourceStorage.store(fileDetailDto.getPath(), multipartFile);
        return fileDetailDto;
    }
}
