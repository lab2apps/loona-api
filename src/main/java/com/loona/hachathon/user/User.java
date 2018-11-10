package com.loona.hachathon.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.loona.hachathon.notification.Notifications;
import com.loona.hachathon.room.Room;
import com.loona.hachathon.space.Space;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vk_user")
@JsonIgnoreProperties(value = { "spaces", "rooms", "notifications" })
public class User {

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "sex")
    private String sex;

    @Column(name = "signedId")
    private String signedId;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "vkUser")
    private List<Space> spaces;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "vkUser")
    private List<Room> rooms;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "vkUser")
    private List<Notifications> notifications;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignedId() {
        return signedId;
    }

    public void setSignedId(String signedId) {
        this.signedId = signedId;
    }

    public List<Space> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<Space> spaces) {
        this.spaces = spaces;
    }

    public List<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notifications> notifications) {
        this.notifications = notifications;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
