package com.studyLog4u.utils.slack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SlackBot {

    @Value("${slack.token}")
    private String slackToken;

}
