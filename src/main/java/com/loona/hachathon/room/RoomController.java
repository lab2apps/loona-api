package com.loona.hachathon.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/{id}")
    public List<Room> getRooms(@PathVariable(value = "id", required = false) String id) {
        return roomService.getRooms(id);
    }

    @PostMapping
    public void createRoom() {

    }

    @PutMapping("/{id}")
    public List<Room> updateRooms(@PathVariable(value = "id") String id) {
        return roomService.getRooms(id);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable(value = "id") String id) {
        roomService.deleteRoom(id);
    }
}
