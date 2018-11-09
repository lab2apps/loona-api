package com.loona.hachathon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @GetMapping("/test")
    public String testDBConnection(){
        return dbUrl;
    }
}
