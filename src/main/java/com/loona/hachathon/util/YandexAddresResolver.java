package com.loona.hachathon.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class YandexAddresResolver {

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @PostConstruct
    public void setUp() {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
    }

//    https://geocode-maps.yandex.ru/1.x/
//
//
////    public void
//
//    private boolean isNotificationsAllowed(String userId) {
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/apps.isNotificationsAllowed")
//                .queryParam("user_id", userId)
//                .queryParam("access_token", serviceKey)
//                .queryParam("v", apiVersion);
//
//
//        String body = restTemplate.getForEntity(builder.toUriString(), String.class).getBody();
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            JsonNode jsonNode = mapper.readTree(body);
//            return jsonNode.get("response").get("is_allowed").asBoolean();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
