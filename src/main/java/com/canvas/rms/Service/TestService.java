package com.canvas.rms.Service;

import com.canvas.rms.Entity.Student;
import com.canvas.rms.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        System.out.println("Students data retrieved: " + students);
        return students;
    }
}
