package com.wj2025.mobileclass.service.IService.permission;

import com.wj2025.mobileclass.model.permission.PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPermissionService extends JpaRepository<PermissionModel,Long> {
}
