package com.loona.hachathon.order;

import com.loona.hachathon.notification.NotificationService;
import com.loona.hachathon.room.Room;
import com.loona.hachathon.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderScheduler {

    private static Logger logger = LoggerFactory.getLogger(OrderScheduler.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Scheduled(fixedDelayString = "300000")
    public void handleOldOrdersAndInvalidate() {
        logger.info("Started invalidation orders job");
        userRepository.findAll().forEach(it -> {
            List<Order> orders = orderRepository.findAllByVkUser(it);
            orders.forEach(order -> {
                if (order != null) {
                    if (order.getStatus() == 1 && order.getTimestamp().plusMinutes(5).isBefore(LocalDateTime.now())) {
                        order.setStatus(3);
                        orderRepository.save(order);
                        Room room = order.getOrderedRoom();
                        notificationService.notifyRoomRenter(room.getVkUser().getId(), room.getRoomSpace().getUuid(), room.getUuid(),
                                "ORDER_SYSTEM_INVALIDATION");
                    }
                }
            });
        });
    }

}
