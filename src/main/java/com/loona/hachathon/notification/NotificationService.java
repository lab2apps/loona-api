package com.loona.hachathon.notification;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class NotificationService {

    private static Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Value("${application.serviceKey}")
    private String serviceKey;

    private VkApiClient vkClient;

    @PostConstruct
    public void setUp() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vkClient = new VkApiClient(transportClient);
    }

    public void sendNotification() {
//        vkClient.apps().
    }
}
