package com.loona.hachathon.order;

import java.time.LocalDateTime;
import java.util.Date;

public class OrderRequestDto {

    private String roomId;
    private Date startRentTime;
    private Date endRentTime;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Date getStartRentTime() {
        return startRentTime;
    }

    public void setStartRentTime(Date startRentTime) {
        this.startRentTime = startRentTime;
    }

    public Date getEndRentTime() {
        return endRentTime;
    }

    public void setEndRentTime(Date endRentTime) {
        this.endRentTime = endRentTime;
    }
}
