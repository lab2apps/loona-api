package com.loona.hachathon.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.loona.hachathon.order.Order;
import com.loona.hachathon.space.Space;
import com.loona.hachathon.user.User;
import com.loona.hachathon.util.CsvAttributeConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "room")
@JsonIgnoreProperties(value = { "orders", "vkUser", "roomSpace" })
public class Room {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "image_urls")
//    @Convert(converter = CsvAttributeConverter.class)
    private String imageUrls;

    @Column(name = "description")
    private String description;

    @Column(name = "floor")
    private String floor;

    @Column(name = "footage")
    private String footage;

    @Column(name = "rent_type", nullable = false)
    private String rentType;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "booking_type")
    private String bookingType;

    @Column(name = "options")
    @Convert(converter = CsvAttributeConverter.class)
    private Set<String> options;

    @ManyToOne
    @JoinColumn(name="space_uuid")
    private Space roomSpace;

    @ManyToOne
    @JoinColumn(name="vk_user_id")
    private User vkUser;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "orderedRoom")
    private List<Order> orders;

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

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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

    public Space getRoomSpace() {
        return roomSpace;
    }

    public void setRoomSpace(Space roomSpace) {
        this.roomSpace = roomSpace;
    }

    public User getVkUser() {
        return vkUser;
    }

    public void setVkUser(User vkUser) {
        this.vkUser = vkUser;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
