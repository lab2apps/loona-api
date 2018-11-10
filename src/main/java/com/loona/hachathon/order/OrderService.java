package com.loona.hachathon.order;

import com.loona.hachathon.exception.BadRequestException;
import com.loona.hachathon.room.Room;
import com.loona.hachathon.room.RoomService;
import com.loona.hachathon.user.User;
import com.loona.hachathon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    public void createOrder(OrderRequestDto orderDto) {
        String currentUserId = getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);
        Room room = roomService.getRoom(orderDto.getRoomId());
        if (room == null || currentUser == null) {
            int rentTime;
            if (room.getRentType().equals("HOUR")) {
                rentTime = orderDto.getEndRentTime().getHour() - orderDto.getStartRentTime().getHour();
            } else {
                rentTime = orderDto.getEndRentTime().getDayOfYear() - orderDto.getStartRentTime().getDayOfYear();
            }
            Order order = new Order();
            order.setStartRentTime(orderDto.getStartRentTime());
            order.setEndRentTime(orderDto.getEndRentTime());
            order.setRentTime(rentTime);
//            order.setPrice(rentTime * room.getPrice());
        } else {
            throw new BadRequestException();
        }
    }

    public List<Order> getMyOrders() {
        String currentUserId = getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);
        if (currentUser != null) {
            return orderRepository.findAllByVkUser(currentUser);
        } else {
            throw new BadRequestException();
        }
    }

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
