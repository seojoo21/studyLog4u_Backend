package com.studyLog4u.slack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studyLog4u.entity.Member;
import com.studyLog4u.entity.Study;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class SlackBotService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.slack.token}")
    private String slackToken;

    @Value("${app.slack.channel-id}")
    private String slackChannelId;

    public void setNotification(Study study){
        String url = "https://slack.com/api/chat.postMessage";
        String studyTitle = study.getTitle();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-Type", "application/json; charset=utf-8");

        Message message = Message.builder()
                .channel(slackChannelId)
                .text(studyTitle + "을/를 복습할 시간입니다.")
                .build();

        HttpEntity<Message> requestEntity = new HttpEntity<>(message, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        log.info("SlackBotService Response :: " + responseEntity.getBody());
    }

//    public String getSlackChannelIdByEmail(String email){
//        String url = "https://slack.com/api/users.lookupByEmail";
//        url += "?email=" + email;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + slackToken);
//        headers.add("Content-Type", "application/x-www-form-urlencoded");
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//            try{
//                JsonNode body = objectMapper.readTree(responseEntity.getBody());
//                return body.get("user").get("id").textValue(); // C03RDJ58082
//            } catch (JsonProcessingException e) {
//                log.error("Json Parsing 에러입니다.");
//                throw new RuntimeException();
//            }
//        }
//        return null;
//    }
}
