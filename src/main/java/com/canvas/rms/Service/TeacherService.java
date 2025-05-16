package com.canvas.rms.Service;

import com.canvas.rms.DTO.CourseDTO;
import com.canvas.rms.DTO.ExamDTO;
import com.canvas.rms.DTO.ResultDTO;
import com.canvas.rms.Entity.*;
import com.canvas.rms.Repository.CourseRepository;
import com.canvas.rms.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private CourseRepository courseRepository;

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(int teacherId) {
        return teacherRepository.findById(teacherId);
    }

    public Teacher getTeacherByUsername(String username) {
        return teacherRepository.findByUsername(username);
    }

    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(int teacherId, Teacher teacher) {
        if (teacherRepository.existsById(teacherId)) {
            teacher.setTeacherId(teacherId);
            return teacherRepository.save(teacher);
        }
        return null;
    }

    public void deleteTeacher(int teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    public boolean authenticate(String username, String password) {
        Teacher teacher = teacherRepository.findByUsername(username);
        return teacher!= null && teacher.getPassword().equals(password);
    }

    // Method to get courses by teacher ID
    public List<CourseDTO> getCoursesByTeacherId(Teacher teacherId) {
        List<Course> courses = courseRepository.findByTeacher(teacherId);
        return courses.stream()
                      .map(this::convertToDTO)
                      .collect(Collectors.toList());
    }

    // Helper method to convert Course entity to CourseDTO
    private CourseDTO convertToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseId(course.getCourseId());
        courseDTO.setCourseName(course.getCourseName());
        courseDTO.setExams(course.getExams().stream()
                                .map(this::convertExamToDTO)
                                .collect(Collectors.toList()));
        courseDTO.setResults(course.getResults().stream()
                                  .map(this::convertResultToDTO)
                                  .collect(Collectors.toList()));
        return courseDTO;
    }

    // Helper method to convert Exam entity to ExamDTO
    private ExamDTO convertExamToDTO(Exam exam) {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setExamId(exam.getExamId());
        examDTO.setExamName(exam.getExamName());
        examDTO.setExamDate(exam.getExamDate());
        examDTO.setMaxMarks(exam.getMaxMarks());
        return examDTO;
    }

    // Helper method to convert Result entity to ResultDTO
    public ResultDTO convertResultToDTO(Result result) {
        ResultDTO resultDTO = new ResultDTO();
        
        // Populate the DTO with the required fields
        resultDTO.setResultId(result.getResultId());
        
        // Set exam information
        Exam exam = result.getExam();
        if (exam != null) {
            resultDTO.setExamId(exam.getExamId());
            resultDTO.setExamName(exam.getExamName());
            resultDTO.setMaxMarks(exam.getMaxMarks());
        }

        // Set student information
        Student student = result.getStudent();
        if (student != null) {
            resultDTO.setStudentId(student.getStudentId());
            resultDTO.setStudentName(student.getFirstName() + " " + student.getLastName());
        }

        resultDTO.setMarksObtained(result.getMarksObtained());

        return resultDTO;
    }
}

