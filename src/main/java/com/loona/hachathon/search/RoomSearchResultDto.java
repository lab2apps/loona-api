package com.loona.hachathon.search;

import com.loona.hachathon.room.RoomDto;

import java.util.List;

public class RoomSearchResultDto {

    private long totalCount;
    private int count;
    private List<RoomDto> rooms;

    public RoomSearchResultDto(long totalCount, int count, List<RoomDto> rooms) {
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

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }
}
