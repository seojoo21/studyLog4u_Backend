package com.studyLog4u.slack;

import com.studyLog4u.entity.Study;
import com.studyLog4u.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SlackBotScheduler {

    private final SlackBotService slackBotService;
    private final StudyRepository studyRepository;

    public void setNotificationSchedule(){
        List<Study> studyList = studyRepository.findAllByNotiDate(LocalDate.now());
        studyList.forEach(slackBotService::setNotification);
    }
}
