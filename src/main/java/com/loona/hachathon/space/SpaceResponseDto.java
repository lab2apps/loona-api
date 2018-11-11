package com.loona.hachathon.space;

import java.util.List;
import java.util.Set;

public class SpaceResponseDto {

    private String uuid;
    private String name;
    private List<String> imageUrls;
    private String type;
    private String description;
    private String address;
    private String phone;
    private String vkLink;
    private Set<String> workDays;
    private String startWorkTime;
    private String endWorkTime;
    private Double latitude;
    private Double longitude;
    private boolean mySpace;
    private boolean myRent;
    private boolean myLike;
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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVkLink() {
        return vkLink;
    }

    public void setVkLink(String vkLink) {
        this.vkLink = vkLink;
    }

    public Set<String> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(Set<String> workDays) {
        this.workDays = workDays;
    }

    public String getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public String getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public boolean isMySpace() {
        return mySpace;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public boolean isMyLike() {
        return myLike;
    }

    public void setMyLike(boolean myLike) {
        this.myLike = myLike;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
