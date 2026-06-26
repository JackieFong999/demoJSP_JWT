package com.ocean.demoJSPJwt.util;

//# 21/05/2026 : BCrypt Password Encoder
//# Web Site : Hash Checking : https://bcrypt-generator.com/

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    // Hash a plain text password
    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }
    
    // Verify a plain text password against a hashed password
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
