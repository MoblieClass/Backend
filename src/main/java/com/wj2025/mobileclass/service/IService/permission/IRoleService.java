package com.wj2025.mobileclass.service.IService.permission;

import com.wj2025.mobileclass.model.permission.RolesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleService extends JpaRepository<RolesModel,Long> {
}
