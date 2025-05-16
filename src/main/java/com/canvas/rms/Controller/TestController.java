package com.canvas.rms.Controller;

import com.canvas.rms.Entity.Student;
import com.canvas.rms.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private TestService testService;

    // This is to test connection to the Spring boot itself
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Server is running");
    }

    // This is to test connection between the Database and Spring Boot
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllEmployees() {
        List<Student> students = testService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // This is to test connection between react and Spring boot
    @PostMapping("/message")
    public ResponseEntity<String> receiveMessage(@RequestBody String message) {
        // Here you can process the message as needed
        System.out.println("Received message: " + message);

        String responseMessage = "Message received: " + message;

        // Optionally, you can respond with a custom message
        return ResponseEntity.ok(responseMessage);
    }
}
