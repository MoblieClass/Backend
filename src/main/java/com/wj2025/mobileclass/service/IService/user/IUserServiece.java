package com.wj2025.mobileclass.service.IService.user;

import com.wj2025.mobileclass.model.user.UserModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserServiece extends JpaRepository<UserModel,Long> {
    Optional<UserModel> findByUsername(String userName);
    Optional<UserModel> findByEmail(String email);
    List<UserModel> findByName(String name, Pageable pageable);
}
