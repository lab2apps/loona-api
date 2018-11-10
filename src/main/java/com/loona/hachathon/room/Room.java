package com.loona.hachathon.room;

import com.loona.hachathon.space.Space;
import com.loona.hachathon.user.User;
import com.loona.hachathon.util.CsvAttributeConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "image_urls")
    @Convert(converter = CsvAttributeConverter.class)
    private List<String> imageUrls;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "rent_type")
    private String rentType;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "start_work_time")
    private int startWorkTime;

    @Column(name = "end_work_time")
    private int endWorkTime;

    @ManyToOne
    @JoinColumn(name="space_uuid")
    private Space roomSpace;

    @ManyToOne
    @JoinColumn(name="vk_user_id")
    private User vkUser;

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

    public Space getRoomSpace() {
        return roomSpace;
    }

    public void setRoomSpace(Space roomSpace) {
        this.roomSpace = roomSpace;
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

    public User getVkUser() {
        return vkUser;
    }

    public void setVkUser(User vkUser) {
        this.vkUser = vkUser;
    }
}
