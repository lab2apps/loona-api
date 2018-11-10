package com.loona.hachathon.notification;

import com.loona.hachathon.user.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "notification")
public class Notifications {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "message")
    private String message;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name="vk_user_id")
    private User vkUser;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getVkUser() {
        return vkUser;
    }

    public void setVkUser(User vkUser) {
        this.vkUser = vkUser;
    }
}
