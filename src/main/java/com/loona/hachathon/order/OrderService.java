package com.loona.hachathon.order;

import com.loona.hachathon.exception.BadRequestException;
import com.loona.hachathon.room.Room;
import com.loona.hachathon.room.RoomService;
import com.loona.hachathon.user.User;
import com.loona.hachathon.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Service
public class OrderService {

    @Value("${application.groupId}")
    private int groupId;

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    // 1 Верефицируется 2 Подтвержден 3 Откланен
    //Rent type HOUR DAY
    public OrderVerifiedDto createOrder(OrderRequestDto orderDto) {
        String currentUserId = getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);
        Room room = roomService.getRoom(orderDto.getRoomId());
        if (room == null || currentUser == null) {
            int rentTime;
            if (room.getRentType().toLowerCase().equals("day")) {
                rentTime = orderDto.getEndRentTime().getDayOfYear() - orderDto.getStartRentTime().getDayOfYear();
            } else {
                rentTime = orderDto.getEndRentTime().getHour() - orderDto.getStartRentTime().getHour();
            }
            int price = rentTime * room.getPrice();
            Order order = new Order();
            order.setRentTime(rentTime);
            order.setStartRentTime(orderDto.getStartRentTime());
            order.setEndRentTime(orderDto.getEndRentTime());
            order.setBookingType(room.getBookingType());
            order.setPrice(price);
            order.setStatus(1);
            order.setVkUser(currentUser);
            order.setOrderedRoom(room);
            orderRepository.save(order);

            userService.addSpacesToFavorite(room.getRoomSpace().getUuid());

            OrderVerifiedDto dto = new OrderVerifiedDto();
            dto.setOrderId(order.getUuid());
            dto.setPrice(price);
            dto.setReceiverId(groupId);
            return dto;
        } else {
            logger.warn("createOrder room or user not found");
            throw new BadRequestException();
        }
    }

    public void submitOrder(String orderId) {
        Order order = orderRepository.findOrderByUuid(orderId);
        if (order != null) {
            order.setStatus(2);
            orderRepository.save(order);
        } else {
            logger.warn("submitOrder order {} not found", orderId);
            throw new BadRequestException();
        }
    }

    public void failOrder(String orderId) {
        Order order = orderRepository.findOrderByUuid(orderId);
        if (order != null) {
            order.setStatus(3);
            orderRepository.save(order);
        } else {
            logger.warn("failOrder order {} not found", orderId);
            throw new BadRequestException();
        }
    }

    public Set<LocalDate> getOrdersByRoom(String roomId, LocalDate fromDate, LocalDate toDate) {
        Room room = roomService.getRoom(roomId);
        if (room != null) {
            List<Order> orders = orderRepository.findAllByOrderedRoomAndEndRentTimeAfterOrAndStartRentTime(room, fromDate.atStartOfDay(), toDate.atStartOfDay());
            Month month = fromDate.getMonth();
            Set<LocalDate> bookedDates = new HashSet<>();
            orders.forEach(it -> {
                LocalDate startTime = it.getStartRentTime().toLocalDate();
                LocalDate endTime = it.getEndRentTime().toLocalDate();
                while (!startTime.isAfter(endTime)) {
                    if (startTime.getMonth().equals(month)) {
                        bookedDates.add(startTime);
                    }
                    startTime = startTime.plusDays(1);
                }
            });
            return bookedDates;
        } else {
            logger.warn("getOrdersByRoom room {} not found", roomId);
            throw new BadRequestException();
        }
    }

    public List<Order> getMyOrders() {
        String currentUserId = getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);
        if (currentUser != null) {
            return orderRepository.findAllByVkUser(currentUser);
        } else {
            logger.warn("getMyOrders user {} not found", currentUserId);
            throw new BadRequestException();
        }
    }

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
