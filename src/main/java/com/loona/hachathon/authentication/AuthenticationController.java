package com.loona.hachathon.authentication;

import com.loona.hachathon.user.UserDto;
import com.loona.hachathon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/authentication", produces = "application/json")
    public String userAuthentication(@RequestBody UserDto userDto) {
        userService.validateExistingUserOrCreate(userDto);
        return jwtTokenUtil.getUserToken(userDto.getId());
    }
}
