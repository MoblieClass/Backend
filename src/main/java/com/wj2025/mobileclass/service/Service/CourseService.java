package com.wj2025.mobileclass.service.Service;

import com.wj2025.mobileclass.model.course.CourseModel;
import com.wj2025.mobileclass.model.course.Course_UserModel;
import com.wj2025.mobileclass.service.IService.course.ICourseService;
import com.wj2025.mobileclass.service.IService.course.ICourse_UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<CourseModel> getAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseService.findAll(pageable).getContent();
    }
    public List<Course_UserModel> getAllCourseUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseUserService.findAll(pageable).getContent();
    }

    public Optional<CourseModel> getCourseById(long id) {
        return courseService.findById(id);
    }


    public List<Course_UserModel> getCourseUserByCourseId(long id) {
        return courseUserService.findByCourseId(id);
    }

    public  List<Course_UserModel> getCourseUserByUserId(long user_id) {
        return courseUserService.findByUserId(user_id);
    }

    public List<CourseModel> getCoursesByCourseName(String courseName,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseService.findByCourseNameContaining(courseName,pageable);
    }

    public List<CourseModel> getCoursesByClassroom(String classroom,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseService.findByClassroomContaining(classroom,pageable);
    }

    public List<CourseModel> getCoursesByCourseType(String courseType,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseService.findByCourseType(courseType,pageable);
    }

    public List<CourseModel> getCoursesByTeacherName(String teacherName,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseService.findByTeacherNameContaining(teacherName,pageable);
    }


    // delete
    public void deleteCourseById(long id) {
        courseService.deleteById(id);
    }
    public void deleteCourseUserById(long id) {
        courseUserService.deleteById(id);
    }
    public void deleteCourseUserByCourseIdAndUserId(long courseId, long userId) {
        List<Course_UserModel> r = courseUserService.findAll();
        for(var u:r){
            if(u.getUserId()==userId && u.getCourseId()==courseId){
                courseUserService.deleteById((long)u.getId());
            }
        }
    }

    // modify
    public void modifyCourse(CourseModel course) {
        courseService.save(course);
    }
    public void modifyCourseUser(Course_UserModel courseUser) {
        courseUserService.save(courseUser);
    }
}
