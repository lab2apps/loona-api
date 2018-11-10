package com.loona.hachathon.order;

import java.time.LocalDateTime;

public class OrderRequestDto {

    private String roomId;
    private LocalDateTime startRentTime;
    private LocalDateTime endRentTime;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getStartRentTime() {
        return startRentTime;
    }

    public void setStartRentTime(LocalDateTime startRentTime) {
        this.startRentTime = startRentTime;
    }

    public LocalDateTime getEndRentTime() {
        return endRentTime;
    }

    public void setEndRentTime(LocalDateTime endRentTime) {
        this.endRentTime = endRentTime;
    }
}
