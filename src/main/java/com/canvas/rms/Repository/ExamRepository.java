package com.canvas.rms.Repository;

import com.canvas.rms.Entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, String> {

    @Query("SELECT e FROM Exam e WHERE e.course.courseId = :courseId")
    List<Exam> findByCourseId(@Param("courseId") int courseId);
}

