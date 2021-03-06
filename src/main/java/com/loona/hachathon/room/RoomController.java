package com.loona.hachathon.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public List<RoomResponseDto> getRooms(@RequestParam(value = "spaceId", required = false) String spaceId) {
        return roomService.getRooms(spaceId);
    }

    @GetMapping("/rooms/my")
    public List<Room> getMyRooms() {
        return roomService.getMyRooms();
    }

    @GetMapping("/room/{id}")
    public RoomResponseDto getRoom(@PathVariable(value = "id") String id) {
        return roomService.getRoomDto(id);
    }

    @PostMapping("/room")
    public void createRoom(@RequestBody RoomDto roomDto) {
        roomService.createRoom(roomDto);
    }

    @PutMapping("/room/{id}")
    public void updateRooms(@PathVariable(value = "id") String id, @RequestBody RoomDto roomDto) {
        roomService.updateRoom(id, roomDto);
    }

    @DeleteMapping(value = "/room/{id}")
    public void deleteRoom(@PathVariable(value = "id") String id) {
        roomService.deleteRoom(id);
    }
}
