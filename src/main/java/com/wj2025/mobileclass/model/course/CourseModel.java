package com.wj2025.mobileclass.model.course;

import jakarta.persistence.*;

@Entity
@Table(name = "xm05_course")
public class CourseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String courseName;
    private String teacherName;
    private String courseType;
    private String classroom;
    private String startTime;
    private String endTime;
    private String startWeek;
    private String endWeek;
    public int getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String course_name) {
        this.courseName = course_name;
    }
    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacher_name) {
        this.teacherName = teacher_name;
    }
    public String getCourseType() {
        return courseType;
    }
    public void setCourseType(String course_type) {
        this.courseType = course_type;
    }
    public String getClassroom() {
        return classroom;
    }
    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String start_time) {
        this.startTime = start_time;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String end_time) {
        this.endTime = end_time;
    }
    public String getStartWeek() {
        return startWeek;
    }
    public void setStartWeek(String start_week) {
        this.startWeek = start_week;
    }
    public String getEndWeek() {
        return endWeek;
    }
    public void setEndWeek(String end_week) {
        this.endWeek = end_week;
    }
}
