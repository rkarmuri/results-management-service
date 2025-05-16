package com.canvas.rms.Service;

import com.canvas.rms.DTO.StudentDTO;
import com.canvas.rms.Entity.Student;
import com.canvas.rms.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    // Method to hash existing passwords in the database
    public void hashExistingPasswords() {
        List<Student> students = studentRepository.findAll();  // Fetch all students
        for (Student student : students) {
            String plainPassword = student.getPassword();
            
            if (plainPassword == null || plainPassword.isEmpty()) {
                logger.warn("Student {} has no password set. Skipping.", student.getUsername());
                continue;  // Skip students without passwords
            }
    
            // Adjust the length based on the hashing algorithm used
            boolean isHashed = plainPassword.length() >= 60;  // Bcrypt hash length heuristic
    
            if (isHashed) {
                // Further validation could be done if specific algorithm info is available
                logger.info("Password for student {} appears to be hashed.", student.getUsername());
            } else {
                logger.info("Password for student {} is not hashed. Hashing now.", student.getUsername());
                String hashedPassword = passwordEncoder.encode(plainPassword);  // Hash password
                student.setPassword(hashedPassword);  // Update with hashed password
                studentRepository.save(student);  // Save updated student back to the database
            }
        }
    }    
    

    public List<StudentDTO> getAllStudents() {
        // Convert List<Student> to List<StudentDTO>
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Optional<StudentDTO> getStudentById(int studentId) {
        return studentRepository.findById(studentId)
                .map(this::convertToDTO);
    }

    public StudentDTO getStudentByUsername(String username) {
        Student student = studentRepository.findByUsername(username);
        return student != null ? convertToDTO(student) : null;
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    public StudentDTO updateStudent(int studentId, StudentDTO studentDTO) {
        Optional<Student> existingStudent = studentRepository.findById(studentId);
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setFirstName(studentDTO.getFirstName());
            student.setLastName(studentDTO.getLastName());
            student.setEmail(studentDTO.getEmail());
            student.setDateOfBirth(studentDTO.getDateOfBirth());
            
            if (studentDTO.getPassword() != null && !studentDTO.getPassword().isEmpty()) {
                student.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
            }
            // Save the updated student back to the repository
            Student updatedStudent = studentRepository.save(student);
            return new StudentDTO(updatedStudent); // Convert entity to DTO if needed
        } else {
            return null;
        }
    }

    public void deleteStudent(int studentId) {
        studentRepository.deleteById(studentId);
    }

    public boolean authenticate(String username, String password) {
        Student student = studentRepository.findByUsername(username);
        return student != null && passwordEncoder.matches(password, student.getPassword());
    }

    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setStudentId(student.getStudentId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setUsername(student.getUsername());
        dto.setEmail(student.getEmail());
        dto.setDateOfBirth(student.getDateOfBirth());
        // Do not set password for DTO
        return dto;
    }

    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setUsername(dto.getUsername());
        student.setPassword(dto.getPassword());
        student.setEmail(dto.getEmail());
        student.setDateOfBirth(dto.getDateOfBirth());
        return student;
    }
}

