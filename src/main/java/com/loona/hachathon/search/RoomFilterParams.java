package com.loona.hachathon.search;

import java.util.List;

public class RoomFilterParams {

    private String roomType;

    private Integer minPrice;
    private Integer maxPrice;
    private String rentType;

    private Integer minFootage;
    private Integer maxFootage;

    private List<String> options;

    private Integer page;
    private Integer pageSize;

    public RoomFilterParams(String roomType, Integer minPrice, Integer maxPrice, String rentType,
                            Integer minFootage, Integer maxFootage, List<String> options,
                            Integer page, Integer pageSize) {
        this.roomType = roomType;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.rentType = rentType;
        this.minFootage = minFootage;
        this.maxFootage = maxFootage;
        this.options = options;
        this.page = page;
        this.pageSize = pageSize;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
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

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType;
    }

    public Integer getMinFootage() {
        return minFootage;
    }

    public void setMinFootage(Integer minFootage) {
        this.minFootage = minFootage;
    }

    public Integer getMaxFootage() {
        return maxFootage;
    }

    public void setMaxFootage(Integer maxFootage) {
        this.maxFootage = maxFootage;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
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
