package com.canvas.rms.Service;

import com.canvas.rms.DTO.ResultDTO;
import com.canvas.rms.Entity.Course;
import com.canvas.rms.Entity.Exam;
import com.canvas.rms.Entity.Result;
import com.canvas.rms.Entity.Student;
import com.canvas.rms.Repository.CourseRepository;
import com.canvas.rms.Repository.ExamRepository;
import com.canvas.rms.Repository.ResultRepository;
import com.canvas.rms.Repository.StudentRepository;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Result saveResult(Result result) {
        // Ensure relationships are set before calculating percentage
        if (result.getStudent() != null && result.getExam() != null) {
            result.setCourse(result.getExam().getCourse()); // Set course from exam
        }
        result.calculatePercentage();
        return resultRepository.save(result);
    }

    public List<ResultDTO> getResultsByCourseId(int courseId) {
        List<Result> results = resultRepository.findByCourse_CourseId(courseId);

        // Convert to ResultDTO
        return results.stream()
                      .map(result -> {
                          ResultDTO resultDTO = new ResultDTO();
                          resultDTO.setStudentId(result.getStudent().getStudentId());
                          resultDTO.setExamId(result.getExam().getExamId());
                          resultDTO.setMarksObtained(result.getMarksObtained());
                          resultDTO.setCourseId(result.getCourse().getCourseId());
                          resultDTO.setMaxMarks(result.getMaxMarks());
                          return resultDTO;
                      })
                      .collect(Collectors.toList());
    }

    public List<ResultDTO> getResultsByCourseIdAndStudentId(int courseId, Integer studentId) {
        return resultRepository.findByCourseIdAndStudentId(courseId, studentId);
    }
    

    public Result getResultById(int resultId) {
        return resultRepository.findById(resultId).orElse(null);
    }

    public void deleteResult(int resultId) {
        resultRepository.deleteById(resultId);
    }

    public void addOrUpdateResultsForCourse(int courseId, List<Result> results) {
        for (Result result : results) {
            Optional<Student> student = studentRepository.findById(result.getStudent().getStudentId());
            Optional<Exam> exam = examRepository.findById(result.getExam().getExamId());
            Optional<Course> course = courseRepository.findById(courseId);

            if (student.isPresent() && exam.isPresent() && course.isPresent() && exam.get().getCourse().getCourseId() == courseId) {
                result.setStudent(student.get());
                result.setExam(exam.get());
                result.setCourse(course.get()); // Set the course directly
                result.calculatePercentage();
                resultRepository.save(result);
            }
        }
    }

    public Result saveOrUpdateResult(Integer studentId, int courseId, String examId, BigDecimal marksObtained) {
        // Check if the result already exists
        Optional<Result> existingResultOpt = resultRepository.findByStudent_StudentIdAndCourse_CourseIdAndExam_ExamId(studentId, courseId, examId);
    
        if (existingResultOpt.isPresent()) {
            // Update the existing result
            Result existingResult = existingResultOpt.get();
            existingResult.setMarksObtained(marksObtained);
            return resultRepository.save(existingResult);
        } else {
            // Create a new result if it doesn't exist
            Result newResult = new Result();
            newResult.setStudent(studentRepository.findById(studentId)
                    .orElseThrow(() -> new EntityNotFoundException("Student not found")));
            newResult.setCourse(courseRepository.findById(courseId)
                    .orElseThrow(() -> new EntityNotFoundException("Course not found")));
            newResult.setExam(examRepository.findById(examId)
                    .orElseThrow(() -> new EntityNotFoundException("Exam not found")));
            newResult.setMarksObtained(marksObtained);
            newResult.setMaxMarks(newResult.getExam().getMaxMarks());
            return resultRepository.save(newResult);
        }
    }    

    public boolean resultExists(Integer studentId, String examId) {
        return resultRepository.existsByStudent_StudentIdAndExam_ExamId(studentId, examId);
    }

    public Result saveResult(ResultDTO resultDTO) {
        if (resultDTO.getStudentId() == null || resultDTO.getExamId() == null || resultDTO.getMarksObtained() == null || resultDTO.getCourseId() == null) {
            throw new IllegalArgumentException("Missing required fields");
        }

        // Log the request data
        System.out.println("Received request to save result: {}"+ resultDTO);
        // Fetch related entities
        Student student = studentRepository.findById(resultDTO.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Exam exam = examRepository.findById(resultDTO.getExamId())
                .orElseThrow(() -> new EntityNotFoundException("Exam not found"));

        Course course = courseRepository.findById(resultDTO.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        // Validate if marks obtained exceed the max marks for the exam
        if (resultDTO.getMarksObtained().compareTo(exam.getMaxMarks()) > 0) {
            throw new IllegalArgumentException("Marks obtained cannot exceed the maximum marks for the exam.");
        }
        
        // Create and populate Result entity
        Result result = new Result();
        result.setStudent(student);
        result.setExam(exam);
        result.setCourse(course);
        result.setMarksObtained(resultDTO.getMarksObtained());

        // Set maxMarks from exam
        result.setMaxMarks(exam.getMaxMarks());
        result.calculatePercentage(); // Calculate percentage

        // Save Result
        return resultRepository.save(result);
    }
}
