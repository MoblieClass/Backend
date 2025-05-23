package com.wj2025.mobileclass.service.IService.course;

import com.wj2025.mobileclass.model.course.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICourseService extends JpaRepository<CourseModel,Long> {
    List<CourseModel> findBycourse_name(String course_name);
    List<CourseModel> findBycourse_type(String course_type);
    List<CourseModel> findByClassroom(String classroom);
    List<CourseModel> findByteacher_name(String teacher_name);
}
