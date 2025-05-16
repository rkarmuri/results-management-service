package com.canvas.rms.DTO;

import java.util.List;

public class CourseDTO {
    private int courseId;
    private String courseName;
    private int teacherId;
    private List<ExamDTO> exams;
    private List<ResultDTO> results;

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public List<ExamDTO> getExams() {
        return exams;
    }

    public void setExams(List<ExamDTO> exams) {
        this.exams = exams;
    }

    public List<ResultDTO> getResults() {
        return results; // Add this method to get results
    }

    public void setResults(List<ResultDTO> results) {
        this.results = results; // Add this method to set results
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", teacher=" + teacherId +
                ", exams=" + exams +
                ", results=" + results +
                '}';
    }
}
