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
import java.util.ArrayList;
import java.util.List;

/**
 * AWS S3 Bucket Management Class
 * reference: https://www.baeldung.com/aws-s3-java
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AmazonS3ResourceStorage {

    @Value("${cloud.aws.bucket-name}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;

    public void storeObject(String fullPath, MultipartFile multipartFile){
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

    public void deleteObject(String fullPath){
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fullPath));
        } catch (Exception e) {
            log.error("AWS Resource Storage Bucket Delete Error :: " + e);
            throw new RuntimeException();
        }
    }

    public void deleteObjectList(String[] files){
        try{
            amazonS3Client.deleteObjects(new DeleteObjectsRequest(bucket).withKeys(files));
        } catch (Exception e){
            log.error("AWS Resource Storage Bucket Delete Objects Error :: " + e);
            throw new RuntimeException();
        }
    }

    public List<String> getObjectList(String prefix){
        List<String> resultList = new ArrayList<>();
        try {
            ObjectListing objectListing = amazonS3Client.listObjects(bucket, prefix);
            for (S3ObjectSummary os : objectListing.getObjectSummaries()){
                resultList.add(os.getKey());
            }
            return resultList;
        } catch (Exception e){
            log.error("AWS Resource Storage Bucket Get Error :: " + e);
            throw new RuntimeException();
        }
    }
}
