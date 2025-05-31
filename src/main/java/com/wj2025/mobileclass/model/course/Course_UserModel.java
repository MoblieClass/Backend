package com.wj2025.mobileclass.model.course;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "xm05_course_user")
public class Course_UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int userId;
    private int courseId;

    public int getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
