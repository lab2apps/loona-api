package com.loona.hachathon.user;

import com.loona.hachathon.util.CsvAttributeConverter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "notification_allowed")
    private boolean notificationAllowed;

    @Column(name = "favorite_spaces")
    @Convert(converter = CsvAttributeConverter.class)
    private Set<String> favoriteSpaces;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNotificationAllowed() {
        return notificationAllowed;
    }

    public void setNotificationAllowed(boolean notificationAllowed) {
        this.notificationAllowed = notificationAllowed;
    }

    public Set<String> getFavoriteSpaces() {
        return favoriteSpaces;
    }

    public void setFavoriteSpaces(Set<String> favoriteSpaces) {
        this.favoriteSpaces = favoriteSpaces;
    }
}
