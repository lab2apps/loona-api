package com.loona.hachathon.notification;

import com.loona.hachathon.user.UserService;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class NotificationController {

    @Autowired
    private UserService userService;

    @GetMapping("/notifications/enable")
    public void enableNotifications() {
        userService.enableNotifications();
    }
}

