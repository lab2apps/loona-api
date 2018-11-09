package com.loona.hachathon.authentication;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class VkUserKeyValidator {

    @Value("${application.id}")
    private String appId;

    @Value("${application.secret}")
    private String appSecret;

    public boolean isKeyValid(String userId, String signedId) {
        String resultString = appId + appSecret + userId;
        byte[] hashedString = Hashing.sha256().hashBytes(resultString.getBytes(Charsets.UTF_8)).asBytes();
        String calculatedHash = new String(Base64.getUrlEncoder().withoutPadding().encode(hashedString));
        return calculatedHash.equals(signedId);
    }

}
