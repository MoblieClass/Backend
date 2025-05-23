package com.wj2025.mobileclass.service.IService.permission;

import com.wj2025.mobileclass.model.permission.PermissionModel;
import com.wj2025.mobileclass.model.permission.Role_PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IRole_PermissionService extends JpaRepository<Role_PermissionModel,Long>{
    Optional<Role_PermissionModel> findBypermission_idAndrole_id(int id, int id1);
    List<Role_PermissionModel> findByrole_id(int roleId);
}
