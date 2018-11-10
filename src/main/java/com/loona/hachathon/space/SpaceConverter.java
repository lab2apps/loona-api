package com.loona.hachathon.space;

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
        space.setImageUrls(spaceDto.getImageUrls());
        space.setStartWorkTime(spaceDto.getStartWorkTime());
        space.setEndWorkTime(spaceDto.getEndWorkTime());

        return space;
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
        return originSpace;
    }
}
