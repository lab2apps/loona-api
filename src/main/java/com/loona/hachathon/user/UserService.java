package com.loona.hachathon.user;

import com.loona.hachathon.authentication.VkUserKeyValidator;
import com.loona.hachathon.exception.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
            if (vkUserKeyValidator.isKeyValid(user.getId(), user.getSignedId())) {
                userRepository.save(user);
                return false;
            } else {
                throw new UnauthorizedAccessException("User signed key validation failed");
            }
        }
    }
}
