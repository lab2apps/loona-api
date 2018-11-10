package com.loona.hachathon.notification;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loona.hachathon.exception.BadRequestException;
import com.loona.hachathon.user.User;
import com.loona.hachathon.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class NotificationService {

    private static Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Value("${application.serviceKey}")
    private String serviceKey;
    @Value("${application.apiVersion}")
    private String apiVersion;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @PostConstruct
    public void setUp() {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
    }

    public void sendNotification(String userId) {
        userId = "14783111";
        if (isNotificationsAllowed(userId)) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/notifications.sendMessage")
                    .queryParam("user_ids", userId)
                    .queryParam("message", "Test notification example")
                    .queryParam("fragment", "/notifications")
                    .queryParam("access_token", serviceKey)
                    .queryParam("v", apiVersion);


            restTemplate.getForEntity(builder.toUriString(), String.class);
        }
    }

    public List<Notifications> getUserNotifications() {
        String currentUserId = getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);
        if (currentUser != null) {
            return notificationRepository.findNotificationsByVkUser(currentUser);
        } else {
            logger.warn("getUserNotifications user {} not found", currentUserId);
            throw new BadRequestException();
        }
    }

    public void addUserNotifications(String userId, String message, String type) {
        User currentUser = userService.getUserById(userId);
        if (currentUser != null) {
            Notifications notifications = new Notifications();
            notifications.setMessage(message);
            notifications.setType(type);
            notifications.setVkUser(currentUser);
            notificationRepository.save(notifications);
        } else {
            logger.warn("addUserNotifications user {} not found", userId);
            throw new BadRequestException();
        }
    }

    private boolean isNotificationsAllowed(String userId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/apps.isNotificationsAllowed")
                .queryParam("user_id", userId)
                .queryParam("access_token", serviceKey)
                .queryParam("v", apiVersion);


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

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
