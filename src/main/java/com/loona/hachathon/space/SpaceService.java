package com.loona.hachathon.space;

import com.loona.hachathon.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    public List<Space> getSpaces() {
        return spaceRepository.findAll();
    }

    public Space getSpace(String spaceId) {
        Space space = spaceRepository.findSpaceByUuid(spaceId);
        if (space == null)
            throw new ResourceNotFound();
        else
            return space;

    }

    public void updateSpace(String spaceId, Space updatedSpace) {
        Space space = spaceRepository.findSpaceByUuid(spaceId);
        if (space == null) {
            throw new ResourceNotFound();
        } else {
            spaceRepository.save(SpaceConverter.merge(space, updatedSpace));
        }
    }

    public void saveSpace(Space space) {
        spaceRepository.save(space);
    }

    public void deleteSpace(String spaceId) {
        Space space = spaceRepository.findSpaceByUuid(spaceId);
        if (space == null) {
            throw new ResourceNotFound();
        } else {
            spaceRepository.deleteById(spaceId);
        }
    }
}
