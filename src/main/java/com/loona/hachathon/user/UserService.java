package com.loona.hachathon.user;

import com.loona.hachathon.authentication.VkUserKeyValidator;
import com.loona.hachathon.exception.UnauthorizedAccessException;
import com.loona.hachathon.space.SpaceService;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Autowired
    private VkUserKeyValidator vkUserKeyValidator;

    public boolean validateExistingUserOrCreate(UserDto userDto) {
        User user = UserConvertor.convert(userDto);
        User currentUser = userRepository.findUserBySignedId(user.getSignedId());
        if (currentUser != null && currentUser.getId().equals(user.getId())) {
            return true;
        } else if (currentUser != null && !currentUser.getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("Invalid user Id");
        } else {
//            if (vkUserKeyValidator.isKeyValid(user.getId(), user.getSignedId())) { //TODO:: check validation
            userRepository.save(user);
            UserSettings userSettings = new UserSettings();
            userSettings.setId(user.getId());
            userSettings.setNotificationAllowed(false);
            userSettingsRepository.save(userSettings);
            return false;
//            } else {
//                throw new UnauthorizedAccessException("User signed key validation failed");
//            }
        }
    }

    public User getUserById(String userId) {
        return userRepository.findUserById(userId);
    }


    public void enableNotifications() {
        String currentUserId = getCurrentUserId();
        logger.info("Enable notifications for user {}", currentUserId);
        UserSettings userSettings = userSettingsRepository.findUserSettingsById(currentUserId);
        userSettings.setNotificationAllowed(true);
        userSettingsRepository.save(userSettings);
    }

    public void addSpacesToFavorite(String spaceId) {
        String currentUserId = getCurrentUserId();
        logger.info("addSpacesToFavorite for user {} space {}", currentUserId, spaceId);
        UserSettings userSettings = userSettingsRepository.findUserSettingsById(currentUserId);
        Set<String> favoriteSpaces = userSettings.getFavoriteSpaces();
        favoriteSpaces.add(spaceId);
        userSettings.setFavoriteSpaces(favoriteSpaces);
        userSettingsRepository.save(userSettings);
    }

    public void deleteSpacesFromFavorite(String spaceId) {
        String currentUserId = getCurrentUserId();
        logger.info("deleteSpacesFromFavorite for user {} space {}", currentUserId, spaceId);
        UserSettings userSettings = userSettingsRepository.findUserSettingsById(currentUserId);
        Set<String> favoriteSpaces = userSettings.getFavoriteSpaces();
        favoriteSpaces.remove(spaceId);
        userSettings.setFavoriteSpaces(favoriteSpaces);
        userSettingsRepository.save(userSettings);
    }

    public boolean isUserExist(String userId) {
        return userRepository.existsById(userId);
    }

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
