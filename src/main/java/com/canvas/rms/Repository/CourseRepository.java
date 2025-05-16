package com.canvas.rms.Repository;

import com.canvas.rms.DTO.BasicCourseDTO;
import com.canvas.rms.Entity.Course;
import com.canvas.rms.Entity.Teacher;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    // You can add custom queries here if needed
    List<Course> findByTeacher(Teacher teacherId);

    @Query("SELECT new com.canvas.rms.DTO.BasicCourseDTO(c.courseId, c.courseName,c.teacher.teacherId) FROM Course c")
    List<BasicCourseDTO> findAllBasicCourses();
}
