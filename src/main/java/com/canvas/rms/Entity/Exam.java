package com.canvas.rms.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @Column(name = "exam_id")
    private String examId;

    @Column(name = "exam_name", length = 100)
    private String examName;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @JsonBackReference
    private Course course;

    @Column(name = "exam_date")
    private java.sql.Date examDate;

    @Column(name = "max_marks")
    private java.math.BigDecimal maxMarks;

    // Getters and Setters
    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public java.sql.Date getExamDate() {
        return examDate;
    }

    public void setExamDate(java.sql.Date examDate) {
        this.examDate = examDate;
    }

    public java.math.BigDecimal getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(java.math.BigDecimal maxMarks) {
        this.maxMarks = maxMarks;
    }
}
