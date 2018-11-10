package com.loona.hachathon.room;

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
        room.setOptions(roomDto.getOptions());
        return room;
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
