package com.loona.hachathon.user;

import com.loona.hachathon.room.Room;
import com.loona.hachathon.space.Space;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vk_user")
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

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "signedId")
    private String signedId;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "vkUser")
    private List<Space> spaces;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
}
