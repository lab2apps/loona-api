package com.loona.hachathon.room;

import com.loona.hachathon.exception.ResourceNotFoundException;
import com.loona.hachathon.space.Space;
import com.loona.hachathon.space.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SpaceService spaceService;

    public List<Room> getRooms() {
        return roomRepository.findAll();
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
        Space space = spaceService.getSpace(roomDto.getSpaceId());
        Room room = RoomConverter.convert(roomDto);
        room.setRoomSpace(space);
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
}
