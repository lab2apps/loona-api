package com.loona.hachathon.space;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.loona.hachathon.room.Room;
import com.loona.hachathon.user.User;
import com.loona.hachathon.util.CsvAttributeConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "space")
@JsonIgnoreProperties(value = { "vkUser", "rooms" })
public class Space {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "image_urls")
//    @Convert(converter = CsvAttributeConverter.class)
    private String imageUrls;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "vk_link")
    private String vkLink;

    @Column(name = "work_days")
    @Convert(converter = CsvAttributeConverter.class)
    private Set<String> workDays;

    @Column(name = "start_work_time")
    private String startWorkTime;

    @Column(name = "end_work_time")
    private String endWorkTime;

    @ManyToOne
    @JoinColumn(name="vk_user_id")
    private User vkUser;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "roomSpace")
    private List<Room> rooms;

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

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
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

    public User getVkUser() {
        return vkUser;
    }

    public void setVkUser(User vkUser) {
        this.vkUser = vkUser;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
