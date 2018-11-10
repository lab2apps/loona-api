package com.loona.hachathon.search;

import com.loona.hachathon.room.Room;

import java.util.List;

public class RoomSearchResultDto {

    private long totalCount;
    private int count;
    private List<Room> rooms;

    public RoomSearchResultDto(long totalCount, int count, List<Room> rooms) {
        this.totalCount = totalCount;
        this.count = count;
        this.rooms = rooms;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
