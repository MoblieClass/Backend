package com.wj2025.mobileclass.service.IService.user;

import com.wj2025.mobileclass.model.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserServiece extends JpaRepository<UserModel,Long> {
}
