package com.loona.hachathon.room;

import com.loona.hachathon.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getRooms(String roomId) {
        if (roomId == null) {
            return roomRepository.findAll();
        } else {
            Room room = roomRepository.findRoomByUuid(roomId);
            if (room == null) {
                throw new ResourceNotFound();
            } else {
                return Collections.singletonList(room);
            }
        }
    }

    public void deleteRoom(String roomId) {
        Room room = roomRepository.findRoomByUuid(roomId);
        if (room == null) {
            throw  new ResourceNotFound();
        } else {
            roomRepository.deleteById(roomId);
        }
    }
}
