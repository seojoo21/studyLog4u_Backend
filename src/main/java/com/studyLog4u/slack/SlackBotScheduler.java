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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Date;
@Slf4j
@RequiredArgsConstructor
@Service
public class SlackBotScheduler {

    private final SlackBotService slackBotService;
    private final StudyRepository studyRepository;

//    @Scheduled(cron = "0 0 20 * * *") // 매일 오후 8시마다
    @Scheduled(cron = "0 * * * * *") // 테스트용 매분 0초마다
    public void setNotificationSchedule() {
        log.info("Quartz Schedule Task Run......");
        LocalDateTime notiDate = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        List<Study> studyList = studyRepository.findAllByNotiDate(notiDate);
        studyList.forEach(slackBotService::setScheduleMessage);
    }
}
