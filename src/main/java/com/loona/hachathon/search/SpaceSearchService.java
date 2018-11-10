package com.loona.hachathon.search;

import com.loona.hachathon.space.Space;
import com.loona.hachathon.space.SpaceConverter;
import com.loona.hachathon.space.SpaceRepository;
import com.loona.hachathon.space.SpaceResponseDto;
import com.loona.hachathon.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SpaceSearchService {

    @Autowired
    private SpaceRepository spaceRepository;

    public List<SpaceResponseDto> search(String spaceName) {
        String currentUserId = getCurrentUserId();
        if (spaceName.isEmpty()) {
            List<SpaceResponseDto> result = new ArrayList<>();
            spaceRepository.findAll().forEach(it -> {
                result.add(SpaceConverter.convert(it, it.getVkUser().getId().equals(currentUserId)));
            });
            return result;
        }

        List<Space> foundSpaces = spaceRepository.findByNameLike(spaceName);

        List<SpaceResponseDto> result = new ArrayList<>();

        for (Space foundSpace : foundSpaces) {
            User vkUser = foundSpace.getVkUser();
            boolean isMySpace = false;
            if (vkUser != null && currentUserId.equals(vkUser.getId())) {
                isMySpace = true;
            }

            result.add(SpaceConverter.convert(foundSpace, isMySpace));
        }

        return result;
    }

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
