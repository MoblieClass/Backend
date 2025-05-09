package com.wj2025.mobileclass.service.IService;

import com.wj2025.mobileclass.model.user.UserModel;

public interface IUserServiece {
    boolean createUser(String username, String password, String email, String phone);
    boolean updateUser(String username, String name,int age,String address,String phone,String email);
    boolean updateUserPassword(String username, String password);
    boolean deleteUser(String username);
    UserModel getUserByUsername(String username);
    UserModel getUserByPhone(String phone);
    UserModel getUserByEmail(String email);
}
