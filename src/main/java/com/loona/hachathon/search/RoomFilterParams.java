package com.loona.hachathon.search;

import java.time.LocalDateTime;

public class RoomFilterParams {

    private LocalDateTime startWorkTime;
    private LocalDateTime endWorkTime;

    private String roomType;

    private String rentType;

    private String name;

    private Integer minPrice;
    private Integer maxPrice;

    private Integer page;
    private Integer pageSize;

    public RoomFilterParams(LocalDateTime startWorkTime, LocalDateTime endWorkTime,
                            String roomType, String rentType, String name,
                            Integer minPrice, Integer maxPrice,
                            Integer page, Integer pageSize) {
        this.startWorkTime = startWorkTime;
        this.endWorkTime = endWorkTime;
        this.roomType = roomType;
        this.rentType = rentType;
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.page = page;
        this.pageSize = pageSize;
    }

    public LocalDateTime getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(LocalDateTime startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public LocalDateTime getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(LocalDateTime endWorkTime) {
        this.endWorkTime = endWorkTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
