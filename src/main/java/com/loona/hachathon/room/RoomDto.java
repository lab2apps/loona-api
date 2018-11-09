package com.loona.hachathon.room;

import java.util.List;

public class RoomDto {

    private String spaceId;

    private String name;

    private List<String> imageUrls;

    private String roomType;

    private String rentType;

    private String description;

    private int price;

    private int startWorkTime;

    private int endWorkTime;

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(int startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public int getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(int endWorkTime) {
        this.endWorkTime = endWorkTime;
    }
}
