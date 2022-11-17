package com.studyLog4u.task;

import com.studyLog4u.aws.AmazonS3ResourceStorage;
import com.studyLog4u.aws.AwsBucketMngService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AwsBucketMngScheduler {

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private final AwsBucketMngService awsBucketMngService;

    @Scheduled(cron = "0 0 2 * * *") // 매일 새벽 2시마다
    public void setAwsBucketMngTaskSchedule(){
        log.info(":::::::AWS Bucket Management Quartz Schedule Task Start:::::::");

        List<String> removeFilesList = awsBucketMngService.createRemoveFileList();

        if(removeFilesList.isEmpty()) {
            log.info("::::::: There's no remove job on "+LocalDate.now() +" :::::::");
        } else {
            log.info("::::::: Remove Job List on "+LocalDate.now()+" :::::::");
            removeFilesList.forEach( file -> log.info(String.valueOf(file)));

            log.info("::::::: Remove Job Starting ::::::: ");
            String[] removeFilesArray = removeFilesList.toArray(new String[0]);
            amazonS3ResourceStorage.deleteObjectList(removeFilesArray);
            log.info("::::::: Remove Job Finish ::::::: ");
        }
        log.info(":::::::AWS Bucket Management Quartz Schedule Task Finish:::::::");
    }
}
