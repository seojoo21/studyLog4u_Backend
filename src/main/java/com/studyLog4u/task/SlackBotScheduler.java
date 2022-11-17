package com.studyLog4u.task;

import com.studyLog4u.entity.Study;
import com.studyLog4u.repository.StudyRepository;
import com.studyLog4u.slack.SlackBotService;
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
    public void setSlackBotAlarmTaskSchedule() {
        log.info(":::::::SlackBot Quartz Schedule Task Start:::::::");
        LocalDateTime notiDate = LocalDate.now().atTime(0,0);

        List<Study> studyList = studyRepository.findAllByNotiDate(notiDate);
        
        if(studyList.isEmpty()) {
            log.info("::::::: There's no study list on "+LocalDate.now() +" :::::::");
        } else {
            log.info(":::::::SlackBot StudyList:::::::");
            studyList.forEach( study -> log.info(String.valueOf(study)));
            log.info(":::::::SlackBot StudyList:::::::");
            studyList.forEach(slackBotService::setScheduleMessage);
        }

        log.info(":::::::SlackBot Quartz Schedule Task Finish:::::::");
    }
}
