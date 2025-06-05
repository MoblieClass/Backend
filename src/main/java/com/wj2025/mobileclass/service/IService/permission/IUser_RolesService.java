package com.wj2025.mobileclass.service.IService.permission;

import com.wj2025.mobileclass.model.permission.User_RolesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUser_RolesService extends JpaRepository<User_RolesModel,Long> {
    List<User_RolesModel> findByUserId(int id);
    List<User_RolesModel> findByUserIdAndRoleId(int userId, int roleId);
}
