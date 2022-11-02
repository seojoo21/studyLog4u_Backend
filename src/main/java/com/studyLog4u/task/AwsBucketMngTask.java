package com.studyLog4u.task;

import com.studyLog4u.entity.FileMng;
import com.studyLog4u.repository.FileMngRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AwsBucketMngTask {

    private final FileMngRepository fileMngRepository;

    @Value("${cloud.aws.bucket-domain}")
    private String bucketDomain;

    @Scheduled(cron = "0 0 2 * * *") // 매일 새벽 2시마다
    public void setAwsBucketMngTaskSchedule(){
        log.info(":::::::AWS Bucket Management Quartz Schedule Task Start:::::::");

        log.info(":::::::AWS Bucket Management Quartz Schedule Task Finish:::::::");
    }
}
