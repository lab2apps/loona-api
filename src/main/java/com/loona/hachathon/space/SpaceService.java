package com.loona.hachathon.space;

import com.loona.hachathon.exception.BadRequestException;
import com.loona.hachathon.exception.ResourceNotFoundException;
import com.loona.hachathon.user.User;
import com.loona.hachathon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private UserService userService;

    public List<Space> getSpaces() {
        return spaceRepository.findAll();
    }

    public Space getSpace(String spaceId) {
        Space space = spaceRepository.findSpaceByUuid(spaceId);
        if (space == null)
            throw new ResourceNotFoundException();
        else
            return space;

    }

    public List<Space> getMySpaces() {
        String currentUserId = getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);
        if (currentUser != null) {
            return spaceRepository.findByVkUser(currentUser);
        } else {
            throw new BadRequestException();
        }
    }

    public void updateSpace(String spaceId, Space updatedSpace) {
        Space space = spaceRepository.findSpaceByUuid(spaceId);
        if (space == null) {
            throw new ResourceNotFoundException();
        } else {
            String currentUserId = getCurrentUserId();
            if (userService.isUserExist(currentUserId) && space.getVkUser().getId().equals(currentUserId)) {
                spaceRepository.save(SpaceConverter.merge(space, updatedSpace));
            } else {
                throw new BadRequestException();
            }
        }
    }

    public void saveSpace(Space space) {
        String currentUserId = getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);
        if (currentUser != null) {
            space.setVkUser(currentUser);
            spaceRepository.save(space);
        } else {
            throw new BadRequestException();
        }
    }

    public void deleteSpace(String spaceId) {
        String currentUserId = getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);
        Space space = spaceRepository.findSpaceByUuidAndVkUser(spaceId, currentUser);
        if (space == null) {
            throw new ResourceNotFoundException();
        } else {
            spaceRepository.deleteById(spaceId);
        }
    }

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
