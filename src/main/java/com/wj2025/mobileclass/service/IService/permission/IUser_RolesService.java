package com.wj2025.mobileclass.service.IService.permission;

import com.wj2025.mobileclass.model.permission.User_RolesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUser_RolesService extends JpaRepository<User_RolesModel,Long> {
    void deleteByUserIdAndRoleId(int user_id, int role_id);
    List<User_RolesModel> findByUserId(int id);
}
