package com.loona.hachathon.notification;

import com.loona.hachathon.order.OrderService;
import com.loona.hachathon.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class NotificationController {

    @Value("${application.serviceKey}")
    private String serviceKey;

    private static Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/notifications/enable")
    public void enableNotifications() {
        userService.enableNotifications();
    }
}

