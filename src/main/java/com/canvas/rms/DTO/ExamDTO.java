package com.canvas.rms.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class ExamDTO {
    private String examId;
    private String examName;
    private int courseId;
    private Date examDate;
    private BigDecimal maxMarks;

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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public BigDecimal getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(BigDecimal maxMarks) {
        this.maxMarks = maxMarks;
    }

    @Override
    public String toString() {
        return "ExamDTO{" +
                "examId='" + examId + '\'' +
                ", examName='" + examName + '\'' +
                ", courseId=" + courseId +
                ", examDate=" + examDate +
                ", maxMarks=" + maxMarks +
                '}';
    }
}
