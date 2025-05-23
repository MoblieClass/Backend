package com.wj2025.mobileclass.service.IService.course;

import com.wj2025.mobileclass.model.course.Course_UserModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICourse_UserService extends JpaRepository<Course_UserModel,Long> {
    List<Course_UserModel> findByUserId(long id, Pageable pageable);
    List<Course_UserModel> findByCourseId(long id, Pageable pageable);
}
