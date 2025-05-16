package com.canvas.rms.Service;

import com.canvas.rms.DTO.ExamDTO;
import com.canvas.rms.Entity.Course;
import com.canvas.rms.Entity.Exam;
import com.canvas.rms.Repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private CourseService courseService;

    public List<ExamDTO> getAllExams() {
        // Convert List<Exam> to List<ExamDTO>
        return examRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<ExamDTO> getExamsByCourseId(int courseId) {
        List<Exam> exams = examRepository.findByCourseId(courseId);
        return exams.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }    

    public Optional<ExamDTO> getExamById(String examId) {
        return examRepository.findById(examId)
                .map(this::convertToDTO);
    }

    public ExamDTO createExam(ExamDTO examDTO) {
        Exam exam = convertToEntity(examDTO);
        Exam savedExam = examRepository.save(exam);
        return convertToDTO(savedExam);
    }

    public ExamDTO updateExam(String examId, ExamDTO examDTO) {
        if (examRepository.existsById(examId)) {
            Exam exam = convertToEntity(examDTO);
            exam.setExamId(examId);
            Exam updatedExam = examRepository.save(exam);
            return convertToDTO(updatedExam);
        }
        return null;
    }

    public void deleteExam(String examId) {
        examRepository.deleteById(examId);
    }

    private ExamDTO convertToDTO(Exam exam) {
        ExamDTO dto = new ExamDTO();
        dto.setExamId(exam.getExamId());
        dto.setExamName(exam.getExamName());
        dto.setCourseId(exam.getCourse().getCourseId());
        dto.setExamDate(exam.getExamDate());
        dto.setMaxMarks(exam.getMaxMarks());
        return dto;
    }

    private Exam convertToEntity(ExamDTO dto) {
        Exam exam = new Exam();
        exam.setExamName(dto.getExamName());
        Course course = courseService.getCourseById(dto.getCourseId()); // Use CourseService to fetch Course
        if (course != null) {
            exam.setCourse(course);
        } else {
            throw new IllegalArgumentException("Invalid course ID: " + dto.getCourseId());
        }
        java.sql.Date sqlDate = new java.sql.Date(dto.getExamDate().getTime());
        exam.setExamDate(sqlDate);
        exam.setMaxMarks(dto.getMaxMarks());
        return exam;
    }

}
