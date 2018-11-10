package com.loona.hachathon.user;

public class UserConvertor {

    public static User convert(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setSex(userDto.getSex());
        user.setCity(userDto.getCity());
        user.setCountry(userDto.getCountry());
        user.setSignedId(userDto.getSignedUserId());
        return user;
    }
}
