package com.loona.hachathon.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, String> {
    UserSettings findUserSettingsById(String id);
    List<UserSettings> findAllByFavoriteSpacesContaining(String favoriteSpaces);
}
