package com.wj2025.mobileclass.service.IService.course;

import com.wj2025.mobileclass.model.course.CourseModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICourseService extends JpaRepository<CourseModel,Long> {
    List<CourseModel> findByCourseNameContaining(String course_name, Pageable pageable);
    List<CourseModel> findByCourseType(String course_type, Pageable pageable);
    List<CourseModel> findByClassroomContaining(String classroom, Pageable pageable);
    List<CourseModel> findByTeacherNameContaining(String teacher_name, Pageable pageable);
}
