package com.loona.hachathon.order;

import com.loona.hachathon.room.Room;
import com.loona.hachathon.user.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "room_order")
public class Order {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "rent_time")
    private int rentTime;

    @Column(name = "price")
    private String price;

    @Column(name = "status")
    private String status;

    @Column(name = "start_rent_time")
    private LocalDateTime startRentTime;

    @Column(name = "end_rent_time")
    private LocalDateTime endRentTime;

    @ManyToOne
    @JoinColumn(name="room_id")
    private Room orderedRoom;

    @ManyToOne
    @JoinColumn(name="vk_user_id")
    private User vkUser;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getRentTime() {
        return rentTime;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Room getOrderedRoom() {
        return orderedRoom;
    }

    public void setOrderedRoom(Room orderedRoom) {
        this.orderedRoom = orderedRoom;
    }

    public User getVkUser() {
        return vkUser;
    }

    public void setVkUser(User vkUser) {
        this.vkUser = vkUser;
    }
}
