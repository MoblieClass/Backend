package com.wj2025.mobileclass.service.IService.permission;

import com.wj2025.mobileclass.model.permission.PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IPermissionService extends JpaRepository<PermissionModel,Long> {
    List<PermissionModel> getPermissionByTitleContaining(String title);
    Optional<PermissionModel> findBypermission_nameContaining(String permission_name);
}
