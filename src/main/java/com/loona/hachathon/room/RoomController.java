package com.loona.hachathon.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<Room> getRooms() {
        return roomService.getRooms();
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable(value = "id", required = false) String id) {
        return roomService.getRoom(id);
    }

    @PostMapping
    public void createRoom(@RequestBody RoomDto roomDto) {
        roomService.createRoom(roomDto);
    }

    @PutMapping("/{id}")
    public void updateRooms(@PathVariable(value = "id") String id, @RequestBody RoomDto roomDto) {
        roomService.updateRoom(id, roomDto);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable(value = "id") String id) {
        roomService.deleteRoom(id);
    }
}
