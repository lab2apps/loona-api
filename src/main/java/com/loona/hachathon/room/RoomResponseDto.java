package com.loona.hachathon.room;

import java.util.List;
import java.util.Set;

public class RoomResponseDto {

    private String uuid;
    private String name;
    private String roomType;
    private List<String> imageUrls;
    private String description;
    private String floor;
    private String footage;
    private String rentType;
    private Integer price;
    private String bookingType;
    private Set<String> options;
    private boolean mySpace;
    private boolean myRent;
    private String userId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFootage() {
        return footage;
    }

    public void setFootage(String footage) {
        this.footage = footage;
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public Set<String> getOptions() {
        return options;
    }

    public void setOptions(Set<String> options) {
        this.options = options;
    }

    public boolean isMySpace() {
        return mySpace;
    }

    public void setMySpace(boolean mySpace) {
        this.mySpace = mySpace;
    }

    public boolean isMyRent() {
        return myRent;
    }

    public void setMyRent(boolean myRent) {
        this.myRent = myRent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
