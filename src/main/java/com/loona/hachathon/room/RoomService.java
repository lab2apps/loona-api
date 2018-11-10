package com.loona.hachathon.room;

import com.loona.hachathon.exception.BadRequestException;
import com.loona.hachathon.exception.ResourceNotFoundException;
import com.loona.hachathon.space.Space;
import com.loona.hachathon.space.SpaceService;
import com.loona.hachathon.user.User;
import com.loona.hachathon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private UserService userService;

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getMyRooms() {
        String currentUserId = getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);
        if (currentUser != null) {
            return roomRepository.findByVkUser(currentUser);
        } else {
            throw new BadRequestException();
        }
    }

    public Room getRoom(String roomId) {
        Room room = roomRepository.findRoomByUuid(roomId);
        if (room == null) {
            throw new ResourceNotFoundException();
        } else {
            return room;
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
    }

    public void updateRoom(String roomId, RoomDto roomDto) {
        Room originRoom = roomRepository.findRoomByUuid(roomId);
        if (originRoom == null) {
            throw new ResourceNotFoundException();
        } else {
            Room updatedRoom = RoomConverter.convert(roomDto);
            roomRepository.save(RoomConverter.merge(originRoom, updatedRoom));
        }
    }

    public void deleteRoom(String roomId) {
        Room room = roomRepository.findRoomByUuid(roomId);
        if (room == null) {
            throw  new ResourceNotFoundException();
        } else {
            roomRepository.deleteById(roomId);
        }
    }

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
