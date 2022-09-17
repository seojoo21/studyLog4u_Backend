package com.studyLog4u.slack;

import com.studyLog4u.entity.Study;
import com.studyLog4u.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class SlackBotScheduler {

    private final SlackBotService slackBotService;

    private final StudyRepository studyRepository;

    @Scheduled(cron = "0 0 20 * * *") // 매일 오후 8시마다
    public void setNotificationSchedule() {
        log.info("Quartz Schedule Task Run Start...");
        LocalDateTime notiDate = LocalDate.now().atTime(0,0);

        List<Study> studyList = studyRepository.findAllByNotiDate(notiDate);
        log.info("===========StudyList=================");
        studyList.forEach( study -> System.out.println(study));
        log.info("===========StudyList=================");

        studyList.forEach(slackBotService::setScheduleMessage);
        log.info("Quartz Schedule Task Run Finish...");
    }
}
