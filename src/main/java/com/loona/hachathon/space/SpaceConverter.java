package com.loona.hachathon.space;

import java.util.Arrays;
import java.util.Collections;

public class SpaceConverter {

    public static Space convert(SpaceDto spaceDto) {
        Space space = new Space();
        space.setName(spaceDto.getName());
        space.setType(spaceDto.getType());
        space.setDescription(spaceDto.getDescription());
        space.setAddress(spaceDto.getAddress());
        space.setPhone(spaceDto.getPhone());
        space.setVkLink(spaceDto.getVkLink());
        space.setWorkDays(spaceDto.getWorkDays());
        if (spaceDto.getImageUrls() != null && !spaceDto.getImageUrls().isEmpty()) {
            space.setImageUrls(spaceDto.getImageUrls());
        } else {
            space.setImageUrls(null);
        }
        space.setStartWorkTime(spaceDto.getStartWorkTime());
        space.setEndWorkTime(spaceDto.getEndWorkTime());

        return space;
    }

    public static SpaceResponseDto convert(Space space, boolean isMySpace, boolean isMyRent) {
        SpaceResponseDto result = new SpaceResponseDto();
        result.setUuid(space.getUuid());
        result.setName(space.getName());
        if (space.getImageUrls() == null) {
            result.setImageUrls(Collections.emptyList());
        } else
            result.setImageUrls(Arrays.asList(space.getImageUrls().split(",")));
        result.setType(space.getType());
        result.setDescription(space.getDescription());
        result.setAddress(space.getAddress());
        result.setPhone(space.getPhone());
        result.setVkLink(space.getVkLink());
        result.setWorkDays(space.getWorkDays());
        result.setStartWorkTime(space.getStartWorkTime());
        result.setEndWorkTime(space.getEndWorkTime());
        result.setLatitude(space.getLatitude());
        result.setLongitude(space.getLongitude());
        result.setMySpace(isMySpace);
        result.setMyRent(isMyRent);
        return result;
    }

    public static Space merge(Space originSpace, Space newSpace) {
        originSpace.setName(newSpace.getName());
        originSpace.setType(newSpace.getType());
        originSpace.setDescription(newSpace.getDescription());
        originSpace.setAddress(newSpace.getAddress());
        originSpace.setPhone(newSpace.getPhone());
        originSpace.setVkLink(newSpace.getVkLink());
        originSpace.setImageUrls(newSpace.getImageUrls());
        originSpace.setWorkDays(newSpace.getWorkDays());
        originSpace.setStartWorkTime(newSpace.getStartWorkTime());
        originSpace.setEndWorkTime(newSpace.getEndWorkTime());
        originSpace.setLongitude(newSpace.getLongitude());
        originSpace.setLatitude(newSpace.getLatitude());
        return originSpace;
    }
}
