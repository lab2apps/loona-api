package com.loona.hachathon.notification;

import com.loona.hachathon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class NotificationController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifications/enable")
    public void enableNotifications() {
        userService.enableNotifications();
    }

    @GetMapping("/notifications")
    public List<Notifications> getUserNotifications() {
        return notificationService.getUserNotifications();
    }

    @GetMapping("/notify")
    public void notifySpaceFollowers(@RequestParam("spaceId") String spaceId, @RequestParam("message") String message) {
        notificationService.notifySpaceFollowers(spaceId, message);
    }
}

