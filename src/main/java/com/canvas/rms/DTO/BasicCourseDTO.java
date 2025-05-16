package com.canvas.rms.DTO;

public class BasicCourseDTO {
    private int courseId;
    private String courseName;
    private int teacherId;

    public BasicCourseDTO(int courseId, String courseName, int teacherId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherId = teacherId;
    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "BasicCourseDTO{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", teacher=" + teacherId +
                '}';
    }
}
