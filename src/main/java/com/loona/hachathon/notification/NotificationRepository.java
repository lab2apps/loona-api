package com.loona.hachathon.notification;

import com.loona.hachathon.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, String> {

    List<Notifications> findNotificationsByVkUserOrderByTimestampDesc(User vkUser);
}
