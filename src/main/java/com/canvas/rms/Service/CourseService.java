package com.canvas.rms.Service;

import com.canvas.rms.DTO.*;
import com.canvas.rms.Entity.Course;
import com.canvas.rms.Entity.Exam;
import com.canvas.rms.Entity.Result;
import com.canvas.rms.Entity.Teacher;
import com.canvas.rms.Repository.CourseRepository;
import com.canvas.rms.Repository.ExamRepository;
import com.canvas.rms.Repository.ResultRepository;
import com.canvas.rms.Repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ResultRepository resultRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<BasicCourseDTO> getAllBasicCourses() {
        return courseRepository.findAllBasicCourses();
    }
    
    public Course getCourseById(Integer courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        return course.orElse(null); // Returns the course if present, otherwise null
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(int courseId, Course course) {
        if (courseRepository.existsById(courseId)) {
            course.setCourseId(courseId);
            return courseRepository.save(course);
        }
        return null;
    }

    public void deleteCourse(int courseId) {
        courseRepository.deleteById(courseId);
    }

    public List<Course> getCoursesByTeacherId(Teacher teacher) {
        return courseRepository.findByTeacher(teacher);
    }

    public void saveCourseDetails(CourseDTO courseDetails) {
        // Save course
        Course course = new Course();
        course.setCourseId(courseDetails.getCourseId());
        course.setCourseName(courseDetails.getCourseName());
        courseRepository.save(course);

        // Save exams
        for (ExamDTO examDTO : courseDetails.getExams()) {
            Exam exam = new Exam();
            exam.setExamId(examDTO.getExamId());
            exam.setExamName(examDTO.getExamName());
            
            // Convert java.util.Date to java.sql.Date
            java.util.Date utilDate = examDTO.getExamDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            exam.setExamDate(sqlDate);
            
            exam.setMaxMarks(examDTO.getMaxMarks());
            exam.setCourse(course);
            examRepository.save(exam);
        }

        for (ResultDTO resultDTO : courseDetails.getResults()) {
            Result result = new Result();
            result.setExam(examRepository.findById(resultDTO.getExamId()).orElse(null));
            result.setStudent(studentRepository.findById(resultDTO.getStudentId()).orElse(null));
            result.setMarksObtained(resultDTO.getMarksObtained());
            resultRepository.save(result);
        }
    }
}
