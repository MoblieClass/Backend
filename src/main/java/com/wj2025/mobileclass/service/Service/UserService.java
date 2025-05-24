package com.wj2025.mobileclass.service.Service;

import com.wj2025.mobileclass.model.permission.RolesModel;
import com.wj2025.mobileclass.model.user.UserModel;
import com.wj2025.mobileclass.service.IService.user.IUserServiece;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final IUserServiece userService;
    public UserService(IUserServiece userService) {
        this.userService = userService;
    }

    public List<UserModel> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.findAll(pageable).getContent();
    }

    public UserModel getUser(int id){
        return userService.findById((long)id).orElse(null);
    }

    public UserModel getUserByUsername(String username){
        return userService.findByUsername(username).orElse(null);
    }

    public List<UserModel> getUserByName(String name,int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return userService.findByName(name,pageable);
    }

    public boolean existsByUsername(String username){
        return getUserByUsername(username) != null;
    }

    public boolean addUser(String username, String password,String name, String phone, String email,int age,String address){
        if(existsByUsername(username)){
            return false;
        }
        UserModel user = new UserModel();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAge(age);
        user.setAddress(address);
        userService.save(user);
        return true;
    }

    public boolean deleteUser(int id){
        Optional<UserModel> user = userService.findById((long) id);
        if(user.isPresent()){
            userService.deleteById((long)user.get().getId());
            return true;
        }
        return false;
    }
    public boolean deleteUserByUsername(String username){
        Optional<UserModel> user = userService.findByUsername(username);
        if(user.isPresent()){
            userService.deleteById((long)user.get().getId());
            return true;
        }
        return false;
    }

    public boolean modifyUser(int id, String password, String name, String phone, String email,int age,String address){
        Optional<UserModel> user = userService.findById((long) id);
        if(user.isPresent()){
            UserModel userModel = user.get();
            userModel.setPassword(password);
            userModel.setName(name);
            userModel.setPhone(phone);
            userModel.setEmail(email);
            userModel.setAge(age);
            userModel.setAddress(address);
            userService.save(userModel);
            return true;
        }
        return false;
    }

    public boolean modifyUserByUsername(String username, String password, String name, String phone, String email,int age,String address){
        Optional<UserModel> user = userService.findByUsername(username);
        if(user.isPresent()){
            UserModel userModel = user.get();
            userModel.setPassword(password);
            userModel.setName(name);
            userModel.setPhone(phone);
            userModel.setEmail(email);
            userModel.setAge(age);
            userModel.setAddress(address);
            userService.save(userModel);
            return true;
        }
        return false;
    }
}
