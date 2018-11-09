package com.loona.hachathon.room;

public class RoomConverter {

    public static Room convert(RoomDto roomDto) {
        Room room = new Room();
        room.setName(roomDto.getName());
        room.setRoomType(roomDto.getRoomType());
        room.setRentType(roomDto.getRentType());
        room.setDescription(roomDto.getDescription());
        room.setImageUrls(roomDto.getImageUrls());
        room.setPrice(roomDto.getPrice());
        room.setStartWorkTime(roomDto.getStartWorkTime());
        room.setEndWorkTime(roomDto.getEndWorkTime());
        return room;
    }

    public static Room merge(Room originRoom, Room updatedRoom) {
        originRoom.setName(updatedRoom.getName());
        originRoom.setRoomType(updatedRoom.getRoomType());
        originRoom.setRentType(updatedRoom.getRentType());
        originRoom.setDescription(updatedRoom.getDescription());
        originRoom.setImageUrls(updatedRoom.getImageUrls());
        originRoom.setPrice(updatedRoom.getPrice());
        originRoom.setStartWorkTime(updatedRoom.getStartWorkTime());
        originRoom.setEndWorkTime(updatedRoom.getEndWorkTime());
        return originRoom;
    }
}
