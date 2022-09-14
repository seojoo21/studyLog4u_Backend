package com.studyLog4u.slack;

import com.studyLog4u.entity.Study;
import com.studyLog4u.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
@Slf4j
@RequiredArgsConstructor
@Service
public class SlackBotScheduler {

    private final SlackBotService slackBotService;
    private final StudyRepository studyRepository;

    @Scheduled(cron = "0 * * * * *")
    public void setNotificationSchedule() {
        log.info("Quartz Schedule Task Run......");
//        System.out.println("notiDate :: " + notiDate);
//        List<Study> studyList = studyRepository.findAllByNotiDate(notiDate);
//        studyList.forEach(slackBotService::setScheduleMessage);
    }
}
