package com.canvas.rms.Service;

import com.canvas.rms.DTO.*;
import com.canvas.rms.Entity.Student;
import com.canvas.rms.Repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHashExistingPasswords() {
        // Arrange
        Student student1 = new Student();
        student1.setUsername("acarter");
        student1.setPassword("student123");

        Student student2 = new Student();
        student2.setUsername("bmiller");
        student2.setPassword("student2024");

        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));
        when(passwordEncoder.encode("student123")).thenReturn("student2024");
        when(passwordEncoder.matches(anyString(), anyString())).thenAnswer(invocation -> {
            String rawPassword = invocation.getArgument(0);
            String encodedPassword = invocation.getArgument(1);
            return rawPassword.equals("student123") && encodedPassword.equals("student2024");
        });

        // Act
        studentService.hashExistingPasswords();

        // Assert
        verify(studentRepository).save(student1);
        verify(studentRepository, never()).save(student2);
    }

    @Test
    public void testCreateStudent() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setUsername("newstudent");
        studentDTO.setPassword("password");

        Student student = new Student();
        student.setUsername("newstudent");
        student.setPassword("hashedpassword");

        when(passwordEncoder.encode("password")).thenReturn("hashedpassword");
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Act
        StudentDTO createdStudent = studentService.createStudent(studentDTO);

        // Assert
        assertEquals("newstudent", createdStudent.getUsername());
        assertEquals("hashedpassword", student.getPassword());
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    public void testAuthenticate() {
        // Arrange
        Student student = new Student();
        student.setUsername("student");
        student.setPassword("hashedpassword");

        when(studentRepository.findByUsername("student")).thenReturn(student);
        when(passwordEncoder.matches("password", "hashedpassword")).thenReturn(true);

        // Act
        boolean authenticated = studentService.authenticate("student", "password");

        // Assert
        assertTrue(authenticated);
    }
}

