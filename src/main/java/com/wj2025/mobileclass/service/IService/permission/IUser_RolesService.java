package com.wj2025.mobileclass.service.IService.permission;

import com.wj2025.mobileclass.model.permission.Role_PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUser_RolesService extends JpaRepository<Role_PermissionModel,Long> {
}
