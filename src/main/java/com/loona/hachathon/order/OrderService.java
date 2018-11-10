package com.loona.hachathon.order;

import com.loona.hachathon.exception.BadRequestException;
import com.loona.hachathon.room.Room;
import com.loona.hachathon.room.RoomService;
import com.loona.hachathon.user.User;
import com.loona.hachathon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Value("${application.groupId}")
    private int groupId;

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
            if (room.getRentType().equals("HOUR")) {
                rentTime = orderDto.getEndRentTime().getHour() - orderDto.getStartRentTime().getHour();
            } else {
                rentTime = orderDto.getEndRentTime().getDayOfYear() - orderDto.getStartRentTime().getDayOfYear();
            }
            int price = rentTime * room.getPrice();
            Order order = new Order();
            order.setRentTime(rentTime);
            order.setStartRentTime(orderDto.getStartRentTime());
            order.setEndRentTime(orderDto.getEndRentTime());
            order.setPrice(price);
            order.setStatus(1);
            order.setVkUser(currentUser);
            order.setOrderedRoom(room);
            orderRepository.save(order);

            userService.addSpacesToFavorite(room.getRoomSpace().getUuid());

            OrderVerifiedDto dto = new OrderVerifiedDto();
            dto.setPrice(price);
            dto.setReceiverId(groupId);
            return dto;
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
