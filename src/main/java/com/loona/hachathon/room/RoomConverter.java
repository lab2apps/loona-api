package com.loona.hachathon.room;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class RoomConverter {

    public static Room convert(RoomDto roomDto) {
        Room room = new Room();
        room.setName(roomDto.getName());
        room.setRoomType(roomDto.getRoomType());
        if (roomDto.getImageUrls() != null && !roomDto.getImageUrls().isEmpty()) {
            roomDto.setImageUrls(roomDto.getImageUrls());
        } else {
            roomDto.setImageUrls(null);
        }
        room.setImageUrls(roomDto.getImageUrls());
        room.setDescription(roomDto.getDescription());
        room.setFloor(roomDto.getFloor());
        room.setFootage(roomDto.getFootage());
        room.setRentType(roomDto.getRentType());
        room.setPrice(roomDto.getPrice());
        room.setBookingType(roomDto.getBookingType());
        room.setOptions(new HashSet<>(roomDto.getOptions()));
        return room;
    }

    public static RoomResponseDto convert(Room room, boolean mySpace, boolean myRent) {
        RoomResponseDto dto = new RoomResponseDto();
        dto.setUuid(room.getUuid());
        dto.setName(room.getName());
        dto.setRoomType(room.getRoomType());
        if (room.getImageUrls() == null) {
            dto.setImageUrls(Collections.emptyList());
        } else
            dto.setImageUrls(Arrays.asList(room.getImageUrls().split(",")));
        dto.setDescription(room.getDescription());
        dto.setFloor(room.getFloor());
        dto.setFootage(room.getFootage());
        dto.setRentType(room.getRentType());
        dto.setPrice(room.getPrice());
        dto.setBookingType(room.getBookingType());
        dto.setOptions(room.getOptions());
        dto.setMySpace(mySpace);
        dto.setMyRent(myRent);
        return dto;
    }

    public static Room merge(Room originRoom, Room updatedRoom) {
        originRoom.setName(updatedRoom.getName());
        originRoom.setRoomType(updatedRoom.getRoomType());
        originRoom.setImageUrls(updatedRoom.getImageUrls());
        originRoom.setDescription(updatedRoom.getDescription());
        originRoom.setFloor(updatedRoom.getFloor());
        originRoom.setFootage(updatedRoom.getFootage());
        originRoom.setRentType(updatedRoom.getRentType());
        originRoom.setPrice(updatedRoom.getPrice());
        originRoom.setBookingType(updatedRoom.getBookingType());
        originRoom.setOptions(updatedRoom.getOptions());
        return originRoom;
    }
}
