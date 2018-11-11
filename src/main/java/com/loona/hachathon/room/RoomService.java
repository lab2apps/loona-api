package com.loona.hachathon.room;

import com.loona.hachathon.exception.BadRequestException;
import com.loona.hachathon.exception.ResourceNotFoundException;
import com.loona.hachathon.notification.NotificationService;
import com.loona.hachathon.order.OrderRepository;
import com.loona.hachathon.order.OrderService;
import com.loona.hachathon.space.Space;
import com.loona.hachathon.space.SpaceService;
import com.loona.hachathon.user.User;
import com.loona.hachathon.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private static Logger logger = LoggerFactory.getLogger(RoomService.class);

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private NotificationService notificationService;

    public List<RoomResponseDto> getRooms(String spaceId) {
        String currentUserId = getCurrentUserId();
        if (spaceId == null) {
            List<RoomResponseDto> roomDto = new ArrayList<>();
            roomRepository.findAll().forEach(it -> {
                roomDto.add(RoomConverter.convert(it, it.getVkUser().getId().equals(currentUserId), orderService.isRentRoom(it.getUuid())));
            });
            return roomDto;
        } else {
            Space space = spaceService.getSpace(spaceId);
            List<RoomResponseDto> roomDto = new ArrayList<>();
            space.getRooms().forEach(it -> {
                roomDto.add(RoomConverter.convert(it, it.getVkUser().getId().equals(currentUserId), orderService.isRentRoom(it.getUuid())));
            });
            return roomDto;
        }
    }

    public List<Room> getMyRooms() {
        String currentUserId = getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);
        if (currentUser != null) {
            return roomRepository.findByVkUser(currentUser);
        } else {
            logger.warn("getMyRooms user {} not found", currentUserId);
            throw new BadRequestException();
        }
    }

    public Room getRoom(String roomId) {
        Room room = roomRepository.findRoomByUuid(roomId);
        if (room == null) {
            logger.warn("getRoom room {} not found", roomId);
            throw new ResourceNotFoundException();
        } else {
            return room;
        }
    }

    public RoomResponseDto getRoomDto(String roomId) {
        String currentUserId = getCurrentUserId();
        Room room = roomRepository.findRoomByUuid(roomId);
        if (room == null) {
            logger.warn("getRoomDto room {} not found", roomId);
            throw new ResourceNotFoundException();
        } else {
            RoomResponseDto dto = RoomConverter.convert(room, room.getVkUser().getId().equals(currentUserId), orderService.isRentRoom(room.getUuid()));
            dto.setUserId(room.getVkUser().getId());
            return dto;
        }
    }

    public void createRoom(RoomDto roomDto) {
        String currentUserId = getCurrentUserId();
        Space space = spaceService.getSpace(roomDto.getSpaceId());
        User currentUser = userService.getUserById(currentUserId);
        Room room = RoomConverter.convert(roomDto);
        room.setRoomSpace(space);
        room.setVkUser(currentUser);
        roomRepository.save(room);
        notificationService.notifyNewRoomAdded(space.getUuid(), room.getUuid());
    }

    public void updateRoom(String roomId, RoomDto roomDto) {
        logger.info("updateRoom room {}", roomId);
        Room originRoom = roomRepository.findRoomByUuid(roomId);
        if (originRoom == null) {
            logger.warn("updateRoom room {} not found", roomId);
            throw new ResourceNotFoundException();
        } else {
            Room updatedRoom = RoomConverter.convert(roomDto);
            roomRepository.save(RoomConverter.merge(originRoom, updatedRoom));
        }
    }

    public void deleteRoom(String roomId) {
        Room room = roomRepository.findRoomByUuid(roomId);
        if (room == null) {
            logger.warn("deleteRoom room {} not found", roomId);
            throw  new ResourceNotFoundException();
        } else {
            room.getOrders().forEach(it -> orderRepository.delete(it));
            roomRepository.deleteById(roomId);
        }
    }

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
