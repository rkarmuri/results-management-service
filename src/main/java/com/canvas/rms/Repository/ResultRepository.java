package com.canvas.rms.Repository;

import com.canvas.rms.DTO.ResultDTO;
import com.canvas.rms.Entity.Result;

import java.util.List;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    boolean existsByStudent_StudentIdAndExam_ExamId(Integer studentId, String examId);
    List<Result> findByCourse_CourseId(int courseId);
    @Query("SELECT new com.canvas.rms.DTO.ResultDTO(r.student.id, r.exam.id, r.marksObtained, r.course.id) " +
           "FROM Result r WHERE r.course.id = :courseId AND r.student.id = :studentId")
    List<ResultDTO> findByCourseIdAndStudentId(@Param("courseId") int courseId, @Param("studentId") int studentId);
    Optional<Result> findByStudent_StudentIdAndCourse_CourseIdAndExam_ExamId(Integer studentId, int courseId, String examId);
}
