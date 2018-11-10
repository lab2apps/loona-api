package com.loona.hachathon.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
public class AddressResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressResolver.class);

    private static final String geocoderUri = "https://geocode-maps.yandex.ru/1.x/";

    @Value("${application.yandexKey}")
    private String yandexKey;

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @PostConstruct
    public void setUp() {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
    }

    public LatLongDto resolveAddress(String address) {
        String addressQuery = address.replaceAll(" ", "+");
        StringBuilder query = new StringBuilder(geocoderUri);
        query.append("?apiKey=")
                .append(yandexKey).append("&format=json")
                .append("&geocode=").append(addressQuery);

        String body = restTemplate.getForEntity(query.toString(), String.class).getBody();

        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(body);
        } catch (IOException e) {
            LOGGER.warn("Cannot get response from Yandex geocoding API for query {}", query.toString());
            return null;
        }

        // Get coordinates
        List<JsonNode> points = jsonNode.findValues("Point");

        if (points.isEmpty()) return null;

        // Take first result
        JsonNode foundPoint = points.get(0);

        // First is longitude, second is latitude
        String coordinatesAsString = foundPoint.get("pos").asText();
        String[] coordinates = coordinatesAsString.split(" ");

        LatLongDto latLongDto = new LatLongDto();
        latLongDto.setLongitude(Double.valueOf(coordinates[0]));
        latLongDto.setLatitude(Double.valueOf(coordinates[1]));

        return latLongDto;
    }
}
