package com.studyLog4u.slack;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;
import com.studyLog4u.entity.Study;
import com.studyLog4u.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class SlackBotService {

    @Value("${app.slack.token}")
    private String slackToken;

    @Value("${app.client.url}")
    private String clientUrl;

    private final ConcurrentMap<String, String> conversationsStore = new ConcurrentHashMap<>();

    public void setScheduleMessage(Study study) {
        String url = "https://slack.com/api/chat.postMessage";
        String studyTitle = study.getTitle();
        String slackChannelId = getSlackChannelId(study);
        log.info("slackChannelId :: " + slackChannelId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-Type", "application/json; charset=utf-8");

        Message message = Message.builder()
                .channel(slackChannelId)
                .text("["+studyTitle+"]"+"을/를 복습할 시간입니다.")
                .attachments(List.of(Attachment.builder()
                        .text("복습하러 가기 >> " + clientUrl +"/study/"+study.getId())
                        .build()))
                .build();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Message> requestEntity = new HttpEntity<>(message, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        log.info("SlackBotService Response :: " + responseEntity.getBody());
    }

    public String getSlackChannelId(Study study) {
        String nickname = StringUtils.lowerCase(study.getNickname());
        String slackChannelId = "";
        var client = Slack.getInstance().methods();

        try {
            var result = client.conversationsList(r->r.token(slackToken));
            saveConversations(result.getChannels());
            if (conversationsStore.get(nickname) != null) {
                slackChannelId = conversationsStore.get(nickname);
            }
        } catch(IOException | SlackApiException e){
            log.error("슬랙 API 호출 중 에러가 발생했습니다. " + e);
        }
        return slackChannelId;
    }

    public void saveConversations(List<Conversation> conversations) {
        for (Conversation conversation : conversations) {
            conversationsStore.put(conversation.getName(), conversation.getId());
        }
    }

}
