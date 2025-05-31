package com.wj2025.mobileclass.service.IService.permission;

import com.wj2025.mobileclass.model.permission.RolesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleService extends JpaRepository<RolesModel,Long> {
    Optional<RolesModel> findByName(String roleName);
}
