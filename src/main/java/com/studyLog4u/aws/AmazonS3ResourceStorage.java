package com.studyLog4u.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.studyLog4u.utils.MultipartFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
@RequiredArgsConstructor
@Slf4j
public class AmazonS3ResourceStorage {

    @Value("${cloud.aws.bucket-name}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;

    public void store(String fullPath, MultipartFile multipartFile){
        File file = new File(MultipartFileUtil.getLocalHomeDirectory(), fullPath);
        try {
            multipartFile.transferTo(file);
            amazonS3Client.putObject(new PutObjectRequest(bucket, fullPath, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            log.error("AWS Resource Storage Bucket Store Error :: " + e);
            throw new RuntimeException();
        } finally {
            if(file.exists()) {
                file.delete();
            }
        }
    }

    public void delete(String fullPath){
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fullPath));
        }
        catch (Exception e) {
            log.error("AWS Resource Storage Bucket Delete Error :: " + e);
            throw new RuntimeException();
        }
    }

//    public void get(String fullPath){
//        S3Object object = amazonS3Client.getObject(new GetObjectRequest(bucket, fullPath));
//    }
}
