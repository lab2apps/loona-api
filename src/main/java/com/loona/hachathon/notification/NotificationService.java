package com.loona.hachathon.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loona.hachathon.exception.BadRequestException;
import com.loona.hachathon.room.Room;
import com.loona.hachathon.room.RoomRepository;
import com.loona.hachathon.space.Space;
import com.loona.hachathon.space.SpaceRepository;
import com.loona.hachathon.user.User;
import com.loona.hachathon.user.UserService;
import com.loona.hachathon.user.UserSettingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
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

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private UserSettingsRepository userSettingsRepository;

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

//    public void notifyNewRoomAdded(String spaceId, String roomId) {
//
//    }

    public void notifyNewRoomAdded(String spaceId, String roomId) {
        userSettingsRepository.findAllByFavoriteSpacesContaining(spaceId).forEach(it -> {
            addUserNotifications(it.getId(), spaceId, roomId, "NEW_ROOM_ADDED");
        });
    }

    private void addUserNotifications(String userId, String spaceId, String roomId, String type) {
        User currentUser = userService.getUserById(userId);
        if (currentUser != null) {
            Notifications notifications = new Notifications();
            notifications.setPayload(getPayload(userId, spaceId, roomId));
            notifications.setType(type);
            notifications.setTimestamp(LocalDateTime.now());
            notifications.setVkUser(currentUser);
            notificationRepository.save(notifications);
        } else {
            logger.warn("addUserNotifications user {} not found", userId);
            throw new BadRequestException();
        }
    }

    private String getPayload(String userId, String spaceId, String roomId) {
        Payload payload = new Payload();
        try {
            Room room = roomRepository.findRoomByUuid(roomId);
            if (room != null) {
                payload.setRoom(objectMapper.writeValueAsString(room));
                payload.setSpace(objectMapper.writeValueAsString(room.getRoomSpace()));
                payload.setUser(objectMapper.writeValueAsString(room.getVkUser()));
                return objectMapper.writeValueAsString(payload);
            }
            Space space = spaceRepository.findSpaceByUuid(spaceId);
            if (space != null) {
                payload.setSpace(objectMapper.writeValueAsString(space));
                payload.setUser(objectMapper.writeValueAsString(space.getVkUser()));
                return objectMapper.writeValueAsString(payload);
            }
            User currentUser = userService.getUserById(userId);
            if (currentUser != null) {
                payload.setUser(objectMapper.writeValueAsString(currentUser));
                return objectMapper.writeValueAsString(payload);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
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
