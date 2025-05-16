package com.canvas.rms.DTO;
import java.math.BigDecimal;

import java.io.Serializable;

public class ResultDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int resultId;
    private Integer studentId;
    private String examId;
    private BigDecimal marksObtained;
    private String examName;
    private BigDecimal maxMarks;
    private String studentName;
    private Integer courseId;

    // Default constructor
    public ResultDTO() {
    }

    // Parameterized constructor
    public ResultDTO(Integer studentId, String examId, BigDecimal marksObtained, Integer courseId) {
        this.studentId = studentId;
        this.examId = examId;
        this.marksObtained = marksObtained;
        this.courseId = courseId;
    }

    // Getters and Setters
    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }
    
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }    
    
    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public BigDecimal getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(BigDecimal marksObtained) {
        this.marksObtained = marksObtained;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public BigDecimal getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(BigDecimal maxMarks) {
        this.maxMarks = maxMarks;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "ResultDTO{" +
                "studentId=" + studentId +
                ", examId='" + examId + '\'' +
                ", marksObtained=" + marksObtained +
                '}';
    }
}

