package com.canvas.rms;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordValidationExample {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Sample hashed password from your database
        String hashedPassword = "$2a$10$XJtdboP69ABlvGlrqiqH/uWzqJdSZKcIm0ffp.owBE6P.reC0wjDm";
        
        // Sample plain password
        String plainPassword = "student123";

        // Check if the plain password matches the hashed password
        boolean matches = passwordEncoder.matches(plainPassword, hashedPassword);
        System.out.println("Password matches: " + matches);
    }
}