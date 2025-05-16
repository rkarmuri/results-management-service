package com.canvas.rms.Controller;

import com.canvas.rms.DTO.ExamDTO;
import com.canvas.rms.Service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAllExams() {
        List<ExamDTO> exams = examService.getAllExams();
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ExamDTO>> getExamsByCourseId(@PathVariable("courseId") int courseId) {
        List<ExamDTO> exams = examService.getExamsByCourseId(courseId);
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable("id") String examId) {
        Optional<ExamDTO> exam = examService.getExamById(examId);
        return exam.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ExamDTO> createExam(@RequestBody ExamDTO examDTO) {
        ExamDTO createdExam = examService.createExam(examDTO);
        return new ResponseEntity<>(createdExam, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable("id") String examId, @RequestBody ExamDTO examDTO) {
        ExamDTO updatedExam = examService.updateExam(examId, examDTO);
        return updatedExam != null ? ResponseEntity.ok(updatedExam) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable("id") String examId) {
        examService.deleteExam(examId);
        return ResponseEntity.noContent().build();
    }
}
