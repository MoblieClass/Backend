package com.wj2025.mobileclass.service.Service;

import com.wj2025.mobileclass.model.course.CourseModel;
import com.wj2025.mobileclass.model.course.Course_UserModel;
import com.wj2025.mobileclass.service.IService.course.ICourseService;
import com.wj2025.mobileclass.service.IService.course.ICourse_UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final ICourseService courseService;
    private final ICourse_UserService courseUserService;

    public CourseService(ICourseService courseService, ICourse_UserService courseUserService) {
        this.courseService = courseService;
        this.courseUserService = courseUserService;
    }

    // add
    public CourseModel addCourse(CourseModel course) {
        return courseService.save(course);
    }
    public Course_UserModel addCourseUser(Course_UserModel courseUser) {
        return courseUserService.save(courseUser);
    }

    // get
    public List<CourseModel> getAllCourses() {
        return courseService.findAll();
    }
    public List<Course_UserModel> getAllCourseUsers() {
        return courseUserService.findAll();
    }

    public Optional<CourseModel> getCourseById(long id) {
        return courseService.findById(id);
    }
    public Optional<Course_UserModel> getCourseUserById(long id) {
        return courseUserService.findById(id);
    }
    public List<Course_UserModel> getCourseUserByUserId(long id) {
        return courseUserService.findByUserId(id);
    }
    public List<Course_UserModel> getCourseUserByCourseId(long id) {
        return  courseUserService.findByCourseId(id);
    }

    public List<CourseModel> getCoursesByCourseName(String courseName) {
        return courseService.findBycourse_name(courseName);
    }

    public List<CourseModel> getCoursesByClassroom(String classroom) {
        return courseService.findByClassroom(classroom);
    }

    public List<CourseModel> getCoursesByCourseType(String courseType) {
        return courseService.findBycourse_type(courseType);
    }

    public List<CourseModel> getCoursesByTeacherName(String teacherName) {
        return courseService.findByteacher_name(teacherName);
    }

    // delete
    public void deleteCourseById(long id) {
        courseService.deleteById(id);
    }
    public void deleteCourseUserById(long id) {
        courseUserService.deleteById(id);
    }

    // modify
    public void modifyCourse(CourseModel course) {
        courseService.save(course);
    }
    public void modifyCourseUser(Course_UserModel courseUser) {
        courseUserService.save(courseUser);
    }
}
