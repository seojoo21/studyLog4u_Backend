package com.studyLog4u.task;

import com.studyLog4u.aws.AmazonS3ResourceStorage;
import com.studyLog4u.entity.FileMng;
import com.studyLog4u.repository.FileMngRepository;
import com.studyLog4u.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AwsBucketMngTask {

    private final FileMngRepository fileMngRepository;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private static final String IMG_ROOT_DIR = "images";

    @Value("${cloud.aws.bucket-domain}")
    private String bucketDomain;

    @Scheduled(cron = "0 0 2 * * *") // 매일 새벽 2시마다
    public void setAwsBucketMngTaskSchedule(){
        log.info(":::::::AWS Bucket Management Quartz Schedule Task Start:::::::");

        // 1. DB에서 어제 날짜로 보관되고 있는 모든 첨부 파일의 목록을 가져온다.
        List<FileMng> dbFileList = fileMngRepository.findAllByPathContains(getTargetDir());

        // 2. DB에서 가져온 fileList 에서 서버 내 파일 경로를 가지고 있는 Path 값만을 가지는 List를 만든다.
        List<String> dbFilePathList = new ArrayList<>();
        for (FileMng file : dbFileList){
            dbFilePathList.add(file.getPath().substring(1));
        }

        // 3. 서버 내 파일 경로에 있는 객체 리스트를 가지고 온다.
        List<String> awsBucketPathList = amazonS3ResourceStorage.getObjectList(getTargetDir());

        // 4. dbFilePathList와 awsBucketPathList를 비교한다.
        // awsBucketPathList에서 dbFilePathList에 없는 파일들을 찾아서 removeFilesList를 만든다.
        List<String> removeFilesList = awsBucketPathList.stream()
                        .filter( path -> !dbFilePathList.contains(path))
                        .collect(Collectors.toList());

        // 5. 앞서 만든 removeFilesList를 String[]로 만든 후 aws 메소드를 이용해 사용하지 않는 파일들을 삭제한다.
        String[] removeFilesArray = removeFilesList.toArray(new String[0]);
        amazonS3ResourceStorage.deleteObjectList(removeFilesArray);

        log.info(":::::::AWS Bucket Management Quartz Schedule Task Finish:::::::");
    }

    private String getTargetDir(){
        LocalDate targetDate = LocalDate.now().minusDays(1);
        String targetDir = CommonUtil.getYYYYMMDDDirPath(targetDate, IMG_ROOT_DIR);
        return targetDir;
    }
}
