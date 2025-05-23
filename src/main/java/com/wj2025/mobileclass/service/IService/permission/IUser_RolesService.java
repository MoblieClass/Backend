package com.wj2025.mobileclass.service.IService.permission;

import com.wj2025.mobileclass.model.permission.User_RolesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUser_RolesService extends JpaRepository<User_RolesModel,Long> {
    void deleteByuser_idAndrole_id(int user_id, int role_id);
}
