package com.canvas.rms.Controller;

import com.canvas.rms.DTO.CourseDTO;
import com.canvas.rms.DTO.ExamDTO;
import com.canvas.rms.DTO.ResultDTO;
import com.canvas.rms.DTO.StudentDTO;
import com.canvas.rms.Entity.Teacher;
import com.canvas.rms.Repository.*;
import com.canvas.rms.Service.ExamService;
import com.canvas.rms.Service.ResultService;
import com.canvas.rms.Service.StudentService;
import com.canvas.rms.Service.TeacherService;
import com.canvas.rms.Entity.Course;
import com.canvas.rms.Entity.Exam;
import com.canvas.rms.Entity.Result;
import com.canvas.rms.Entity.Student;

import jakarta.persistence.EntityNotFoundException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.math.BigDecimal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ResultService resultService;

     @Autowired
    private StudentService studentService;

    @Autowired
    private ExamService examService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Username: " + loginRequest.getUsername());
        System.out.println("Password: " + loginRequest.getPassword());

        boolean isAuthenticated = teacherService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok(new SuccessResponse("Login successful")); // or return a token if using JWT
        } else {
            return ResponseEntity.status(401).body(new ErrorResponse("Invalid username or password"));
        }
    }
    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") int teacherId) {
        Optional<Teacher> teacher = teacherService.getTeacherById(teacherId);
        return teacher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/{username}")
    public ResponseEntity<Teacher> getTeacherByUsername(@PathVariable("username") String username) {
        Teacher teacher = teacherService.getTeacherByUsername(username);
        return teacher != null ? ResponseEntity.ok(teacher) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        Teacher createdTeacher = teacherService.createTeacher(teacher);
        return new ResponseEntity<>(createdTeacher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") int teacherId, @RequestBody Teacher teacher) {
        Teacher updatedTeacher = teacherService.updateTeacher(teacherId, teacher);
        return updatedTeacher != null ? ResponseEntity.ok(updatedTeacher) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("id") int teacherId) {
        teacherService.deleteTeacher(teacherId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/courses/{teacherId}")
    public ResponseEntity<List<CourseDTO>> getCoursesByTeacherId(@PathVariable Teacher teacherId) {
        List<CourseDTO> courses = teacherService.getCoursesByTeacherId(teacherId);
        if (courses != null) {
            return ResponseEntity.ok(courses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/results")
    public ResponseEntity<String> enterResult(@RequestBody ResultDTO resultDTO) {
        try {
            resultService.saveResult(resultDTO);
            return ResponseEntity.ok("Result entered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to enter result" + e.getMessage());
        }
    }

    @GetMapping("/results/check")
    public ResponseEntity<Boolean> checkResultExists(
            @RequestParam int studentId,
            @RequestParam String examId) {
        boolean exists = resultService.resultExists(studentId, examId);
        return ResponseEntity.ok(exists);
    }

    // Endpoints for managing students
    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable("id") int studentId) {
        Optional<StudentDTO> student = studentService.getStudentById(studentId);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/students")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable("id") int studentId, @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(studentId, studentDTO);
        return updatedStudent != null ? ResponseEntity.ok(updatedStudent) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") int studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    // Endpoints for managing exams
    @GetMapping("/exams")
    public ResponseEntity<List<ExamDTO>> getAllExams() {
        List<ExamDTO> exams = examService.getAllExams();
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @GetMapping("/exams/course/{courseId}")
    public ResponseEntity<List<ExamDTO>> getExamsByCourseId(@PathVariable int courseId) {
        List<ExamDTO> exams = examService.getExamsByCourseId(courseId);
        if (exams.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/results/course/{courseId}")
    public ResponseEntity<List<ResultDTO>> getResultsByCourseId(@PathVariable("courseId") int courseId) {
        List<ResultDTO> results = resultService.getResultsByCourseId(courseId);
        if (results != null && !results.isEmpty()) {
            return ResponseEntity.ok(results);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/results/update")
    public ResponseEntity<String> updateResults(@RequestBody Map<String, Object> payload) {
        try {
            logger.info("Received payload: {}", payload);

            // Extract courseId from payload
            int courseId = Integer.parseInt(payload.get("courseId").toString());

            // Convert the "results" part of the payload to a List of Map<String, Object>
            List<Map<String, Object>> resultDTOs = objectMapper.convertValue(payload.get("results"), new TypeReference<List<Map<String, Object>>>() {});
            logger.debug("Extracted courseId: {}", courseId);
            logger.debug("Converted results: {}", resultDTOs);

            // Process each result from the list
            for (Map<String, Object> dto : resultDTOs) {
                int studentId = Integer.parseInt(dto.get("studentId").toString());
                String examId = dto.get("examId").toString();
                BigDecimal marksObtained = new BigDecimal(dto.get("marksObtained").toString());

                // Fetch the student, exam, and course entities
                Student student = studentRepository.findById(studentId)
                        .orElseThrow(() -> new EntityNotFoundException("Student not found"));
                Exam exam = examRepository.findById(examId)
                        .orElseThrow(() -> new EntityNotFoundException("Exam not found"));
                Course course = courseRepository.findById(courseId)
                        .orElseThrow(() -> new EntityNotFoundException("Course not found"));

                // Check if the result already exists based on studentId, courseId, and examId
                Optional<Result> existingResultOpt = resultRepository.findByStudent_StudentIdAndCourse_CourseIdAndExam_ExamId(studentId, courseId, examId);
                
                Result result;
                if (existingResultOpt.isPresent()) {
                    // Update the existing result
                    result = existingResultOpt.get();
                    result.setMarksObtained(marksObtained);
                } else {
                    // Create a new result
                    result = new Result();
                    result.setStudent(student);
                    result.setExam(exam);
                    result.setCourse(course);
                    result.setMarksObtained(marksObtained);
                    result.setMaxMarks(exam.getMaxMarks());
                }

                // Save the result (either updated or new)
                resultRepository.save(result);
            }

            return ResponseEntity.ok("Results updated successfully");
        } catch (Exception e) {
            logger.error("Error updating results", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update results: " + e.getMessage());
        }
    }


    @GetMapping("/exams/{id}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable("id") String examId) {
        Optional<ExamDTO> exam = examService.getExamById(examId);
        return exam.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/exams")
    public ResponseEntity<ExamDTO> createExam(@RequestBody ExamDTO examDTO) {
        ExamDTO createdExam = examService.createExam(examDTO);
        return new ResponseEntity<>(createdExam, HttpStatus.CREATED);
    }

    @PutMapping("/exams/{id}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable("id") String examId, @RequestBody ExamDTO examDTO) {
        ExamDTO updatedExam = examService.updateExam(examId, examDTO);
        return updatedExam != null ? ResponseEntity.ok(updatedExam) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/exams/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable("id") String examId) {
        examService.deleteExam(examId);
        return ResponseEntity.noContent().build();
    }
}


class SuccessResponse {
    private String message;
    public SuccessResponse(String message) { this.message = message; }
    public String getMessage() { return message; }
}

class ErrorResponse {
    private String message;
    public ErrorResponse(String message) { this.message = message; }
    public String getMessage() { return message; }
}

