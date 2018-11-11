package com.loona.hachathon.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loona.hachathon.exception.BadRequestException;
import com.loona.hachathon.room.Room;
import com.loona.hachathon.room.RoomRepository;
import com.loona.hachathon.space.Space;
import com.loona.hachathon.space.SpaceRepository;
import com.loona.hachathon.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private UserRepository userRepository;

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @PostConstruct
    public void setUp() {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
    }

    public void notifyNewRoomAdded(String spaceId, String roomId) {
        userSettingsRepository.findAll().forEach(it -> {
            if (it.getFavoriteSpaces().contains(spaceId)) {
                addUserNotifications(it.getId(), spaceId, roomId, "NEW_ROOM_ADDED", null);
                sendNotification(it.getId(), spaceId, "NEW_ROOM_ADDED");
            }
        });
    }

    public void notifySpaceOwnerRoomRenter(String userId, String spaceId, String roomId, String rentStatus) {
        addUserNotifications(userId, spaceId, roomId, rentStatus, null);
    }

    public void notifyUserRoomRenter(String userId, String spaceId, String roomId, String rentStatus) {
        addUserNotifications(userId, spaceId, roomId, rentStatus, null);
    }

    public void notifySpaceFollowers(String spaceId, String message) {
        List<User> users = userRepository.findAll();
        users.stream().map(it -> userSettingsRepository.findUserSettingsById(it.getId()))
                .filter(it -> it.getFavoriteSpaces().contains(spaceId))
                .map(UserSettings::getId)
                .forEach(it -> addUserNotifications(it, spaceId, null, "MESSAGE_SPACE_FOLLOWERS", message));

    }

    public void sendNotification(String userId, String spaceId, String type) {
        if (isNotificationsAllowed(userId)) {
            String message;
            if (type.equals("NEW_ROOM_ADDED") || type.equals("MESSAGE_SPACE_FOLLOWERS")) {
                Space space = spaceRepository.findSpaceByUuid(spaceId);
                message = "Вам поступило уведомление от площадки " + space.getName();
            } else {
                message = "Статус вашей брони изменене";
            }

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/notifications.sendMessage")
                    .queryParam("user_ids", userId)
                    .queryParam("message", message)
                    .queryParam("fragment", "/notifications")
                    .queryParam("access_token", serviceKey)
                    .queryParam("v", apiVersion);

//            restTemplate.getForEntity(builder.toUriString(), String.class); //TODO:: FIX
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

    private void addUserNotifications(String userId, String spaceId, String roomId, String type, String message) {
        User currentUser = userService.getUserById(userId);
        if (currentUser != null) {
            Notifications notifications = new Notifications();
            notifications.setPayload(getPayload(userId, spaceId, roomId, message));
            notifications.setType(type);
            notifications.setTimestamp(LocalDateTime.now());
            notifications.setVkUser(currentUser);
            notificationRepository.save(notifications);
            sendNotification(currentUser.getId(), spaceId, type);
        } else {
            logger.warn("addUserNotifications user {} not found", userId);
            throw new BadRequestException();
        }
    }

    private String getPayload(String userId, String spaceId, String roomId, String message) {
        Payload payload = new Payload();
        payload.setMessage(message);
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
