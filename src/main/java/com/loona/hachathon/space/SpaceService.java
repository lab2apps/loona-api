package com.loona.hachathon.space;

import com.loona.hachathon.exception.BadRequestException;
import com.loona.hachathon.exception.ResourceNotFoundException;
import com.loona.hachathon.room.RoomService;
import com.loona.hachathon.user.User;
import com.loona.hachathon.user.UserService;
import com.loona.hachathon.util.AddressResolver;
import com.loona.hachathon.util.LatLongDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpaceService {

    private static Logger logger = LoggerFactory.getLogger(SpaceService.class);

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressResolver addressResolver;

    public List<SpaceResponseDto> getSpaces() {
        String currentUserId = getCurrentUserId();
        List<SpaceResponseDto> spaceDto = new ArrayList<>();
        spaceRepository.findAll().forEach(it -> {
            spaceDto.add(SpaceConverter.convert(it, it.getVkUser().getId().equals(currentUserId)));
        });
        return spaceDto;
    }

    public SpaceResponseDto getSpaceDto(String spaceId) {
        String currentUserId = getCurrentUserId();
        Space space = spaceRepository.findSpaceByUuid(spaceId);
        if (space == null) {
            logger.warn("getSpaceDto space {} not found", spaceId);
            throw new ResourceNotFoundException();
        } else
            return SpaceConverter.convert(space, space.getVkUser().getId().equals(currentUserId));

    }

    public Space getSpace(String spaceId) {
        Space space = spaceRepository.findSpaceByUuid(spaceId);
        if (space == null) {
            logger.warn("GetSpace space {} not found", spaceId);
            throw new ResourceNotFoundException();
        } else
            return space;

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
            logger.warn("GetMySpaces space user {} not found", currentUserId);
            throw new BadRequestException();
        }
    }

    public void updateSpace(String spaceId, Space updatedSpace) {
        Space space = spaceRepository.findSpaceByUuid(spaceId);
        if (space == null) {
            logger.warn("Updating space {} not found", spaceId);
            throw new ResourceNotFoundException();
        }

        if (updatedSpace.getAddress() != null) {
            LatLongDto updatedLatLongDto = addressResolver.resolveAddress(updatedSpace.getAddress());
            if (updatedLatLongDto != null) {
                updatedSpace.setLatitude(updatedLatLongDto.getLatitude());
                updatedSpace.setLongitude(updatedLatLongDto.getLongitude());
            } else {
                updatedSpace.setLatitude(null);
                updatedSpace.setLongitude(null);
            }
        }

        spaceRepository.save(SpaceConverter.merge(space, updatedSpace));
    }

    public void saveSpace(Space space) {
        String currentUserId = getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);

        if (currentUser == null) {
            logger.warn("Saving space user {} not found", currentUserId);
            throw new BadRequestException();
        }

        space.setVkUser(currentUser);

        LatLongDto latLongDto = addressResolver.resolveAddress(space.getAddress());

        if (latLongDto != null) {
            space.setLatitude(latLongDto.getLatitude());
            space.setLongitude(latLongDto.getLongitude());
        }

        spaceRepository.save(space);
    }

    public void deleteSpace(String spaceId) {
        String currentUserId = getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);
        Space space = spaceRepository.findSpaceByUuidAndVkUser(spaceId, currentUser);
        if (space == null) {
            logger.warn("Deleting space {} not found", spaceId);
            throw new ResourceNotFoundException();
        } else {
            space.getRooms().forEach(it -> {
                roomService.deleteRoom(it.getUuid());
            });
            spaceRepository.deleteById(spaceId);
        }
    }

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
