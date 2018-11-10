package com.loona.hachathon.order;

import com.loona.hachathon.room.Room;
import com.loona.hachathon.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findAllByOrderedRoomAndEndRentTimeAfterOrAndStartRentTime(Room orderedRoom, LocalDate fromDate, LocalDate toDate);
    Order findOrderByUuid(String uuid);
    List<Order> findAllByVkUser(User vkUser);
}
