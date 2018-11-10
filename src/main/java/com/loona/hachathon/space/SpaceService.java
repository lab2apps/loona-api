package com.loona.hachathon.space;

import com.loona.hachathon.exception.BadRequestException;
import com.loona.hachathon.exception.ResourceNotFoundException;
import com.loona.hachathon.user.User;
import com.loona.hachathon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private UserService userService;

    public List<SpaceResponseDto> getSpaces() {
        String currentUserId = getCurrentUserId();
        List<SpaceResponseDto> spaceDto = new ArrayList<>();
        spaceRepository.findAll().forEach(it -> {
            spaceDto.add(SpaceConverter.convert(it, it.getVkUser().getId().equals(currentUserId)));
        });
        return spaceDto;
    }

    public SpaceResponseDto getSpace(String spaceId) {
        String currentUserId = getCurrentUserId();
        Space space = spaceRepository.findSpaceByUuid(spaceId);
        if (space == null)
            throw new ResourceNotFoundException();
        else
            return SpaceConverter.convert(space, space.getVkUser().getId().equals(currentUserId));

    }

    public List<SpaceResponseDto> getMySpaces() {
        String currentUserId = getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);
        if (currentUser != null) {
            List<SpaceResponseDto> spaceDto = new ArrayList<>();
            spaceRepository.findByVkUser(currentUser).forEach(it -> {
                spaceDto.add(SpaceConverter.convert(it, it.getVkUser().getId().equals(currentUserId)));
            });
            return spaceDto;
        } else {
            throw new BadRequestException();
        }
    }

    public void updateSpace(String spaceId, Space updatedSpace) {
        Space space = spaceRepository.findSpaceByUuid(spaceId);
        if (space == null) {
            throw new ResourceNotFoundException();
        } else {
            spaceRepository.save(SpaceConverter.merge(space, updatedSpace));
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
