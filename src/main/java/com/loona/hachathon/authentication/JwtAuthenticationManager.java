package com.loona.hachathon.authentication;

import com.loona.hachathon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtAuthenticationManager implements AuthenticationManager {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                String token = (String)authentication.getCredentials();
                if (token == null) {
                    throw new AuthenticationServiceException("Empty token");
                } else {
                    String userId = jwtTokenUtil.getUserId(token);
//                    || !userService.isUserExist(userId) //TODO:: Add back
                    if (userId == null) {
                        throw new AuthenticationServiceException("UserId in token doesn't exist");
                    } else {
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                        return new UsernamePasswordAuthenticationToken(userId, token, authorities);
                    }
                }
            } else {
                throw new AuthenticationServiceException("Authentication not supported");
            }
        } catch (Exception ex) {
            throw new AuthenticationServiceException(ex.getMessage());
        }
    }
}
