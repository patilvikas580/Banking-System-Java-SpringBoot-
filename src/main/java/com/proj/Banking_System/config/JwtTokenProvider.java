package com.proj.Banking_System.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${}")
    private String jwtSecret;
    private String jwtToken;
}
