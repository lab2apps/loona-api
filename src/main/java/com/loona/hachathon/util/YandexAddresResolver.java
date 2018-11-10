package com.loona.hachathon.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class YandexAddresResolver {

    @Value("${application.yandexKey}")
    private String yandexKey;

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
    private boolean resolveAddress(String addres) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://geocode-maps.yandex.ru/1.x/")
                .queryParam("apikey", yandexKey)
                .queryParam("geocode", addres);


        String body = restTemplate.getForEntity(builder.toUriString(), String.class).getBody();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(body);
            return jsonNode.get("response").get("is_allowed").asBoolean();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
