package com.canvas.rms.Controller;

import com.canvas.rms.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/hash-passwords")
    @PreAuthorize("hasRole('ADMIN')") // Ensure only authorized users can access this endpoint
    public String hashPasswords() {
        studentService.hashExistingPasswords();
        return "Passwords have been hashed and updated.";
    }
}

