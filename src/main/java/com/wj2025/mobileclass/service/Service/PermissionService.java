package com.wj2025.mobileclass.service.Service;

import com.wj2025.mobileclass.model.permission.PermissionModel;
import com.wj2025.mobileclass.model.permission.Role_PermissionModel;
import com.wj2025.mobileclass.model.permission.RolesModel;
import com.wj2025.mobileclass.model.permission.User_RolesModel;
import com.wj2025.mobileclass.service.IService.permission.IPermissionService;
import com.wj2025.mobileclass.service.IService.permission.IRoleService;
import com.wj2025.mobileclass.service.IService.permission.IRole_PermissionService;
import com.wj2025.mobileclass.service.IService.permission.IUser_RolesService;
import com.wj2025.mobileclass.service.IService.user.IUserServiece;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {
    private final IPermissionService permissionService;
    private final IRoleService roleService;
    private final IRole_PermissionService role_permissionService;
    private final IUser_RolesService user_rolesService;
    private final IUserServiece userService;

    public PermissionService(IPermissionService permissionService,IRoleService roleService,IRole_PermissionService role_permissionService,IUser_RolesService user_rolesService,IUserServiece userService) {
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.role_permissionService = role_permissionService;
        this.user_rolesService = user_rolesService;
        this.userService = userService;
    }

    public List<RolesModel> getAllRoles(){
        return roleService.findAll();
    }
    public List<PermissionModel>getAllPermissions(){
        return permissionService.findAll();
    }

    public Optional<RolesModel>getRole(String roleName){
        return roleService.findByName(roleName);
    }

    public Optional<PermissionModel>getPermission(String permissionName){
        return permissionService.findByPermissionName(permissionName);
    }

    public boolean addPermission(String title, String description,String permission_name) {
        var permissions = permissionService.findByPermissionNameContaining(permission_name);
        if (permissions.isPresent()) {
            return false;
        }
        var permissionModel = new PermissionModel();
        permissionModel.setTitle(title);
        permissionModel.setDescription(description);
        permissionModel.setPermissionName(permission_name);
        permissionService.save(permissionModel);
        return true;
    }

    public boolean deletePermission(String permission_name) {
        var permissions = permissionService.findByPermissionNameContaining(permission_name);
        if (permissions.isPresent()) {
            permissionService.delete(permissions.get());
            return true;
        }
        return false;
    }

    public List<PermissionModel> getPermissionByTitle(String title) {
        return permissionService.findByTitleContaining(title);
    }

    public List<RolesModel> getUserRoles(String username) {
        var users = userService.findByUsername(username);
        if (users.isEmpty()) {
            return new ArrayList<>();
        }
        var roles_links = user_rolesService.findByUserId(users.get().getId());
        var rolesModels = new ArrayList<RolesModel>();
        for (var role_link : roles_links) {
            Optional<RolesModel> role = roleService.findById((long)role_link.getRoleId());
            role.ifPresent(rolesModels::add);
        }
        return rolesModels;
    }

    public List<PermissionModel> getPermissionByRoleId(int id) {
        var permissions_links = role_permissionService.findByRoleId(id);
        var permissionsModels = new ArrayList<PermissionModel>();
        for (var permission_link : permissions_links) {
            Optional<PermissionModel> permission = permissionService.findById((long)permission_link.getPermissionId());
            permission.ifPresent(permissionsModels::add);
        }
        return permissionsModels;
    }

    public RolesModel addRole(String role_name,String description) {
        var role = new RolesModel();
        role.setName(role_name);
        role.setDescription(description);
        return roleService.save(role);
    }

    public boolean deleteRole(String role_name) {
        var role = roleService.findByName(role_name);
        if(role.isPresent()) {
            roleService.delete(role.get());
            return true;
        }
        return false;
    }

    public boolean addPermissionToRole(String role_name, String permission_name) {
        var permission = permissionService.findByPermissionNameContaining(permission_name);
        if(permission.isPresent()) {
            var role = roleService.findByName(role_name);
            if(role.isPresent()) {
                var role_permission = new Role_PermissionModel();
                role_permission.setPermissionId(permission.get().getId());
                role_permission.setRoleId(role.get().getId());
                role_permissionService.save(role_permission);
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean deletePermissionFromRole(String role_name, String permission_name) {
        var permission = permissionService.findByPermissionNameContaining(permission_name);
        if(permission.isPresent()) {
            var role = roleService.findByName(role_name);
            if(role.isPresent()) {
                var role_permission = role_permissionService.findByPermissionIdAndRoleId(permission.get().getId(),role.get().getId());
                if(role_permission.isPresent()) {
                    role_permissionService.deleteById((long)role_permission.get().getId());
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public boolean addRoleToUser(String role_name, String user_name) {
        var role = roleService.findByName(role_name);
        if(role.isPresent()) {
            var user = userService.findByUsername(user_name);
            if(user.isPresent()) {
                User_RolesModel user_rolesModel = new User_RolesModel();
                user_rolesModel.setRoleId(role.get().getId());
                user_rolesModel.setUserId(user.get().getId());
                user_rolesService.save(user_rolesModel);
                return true;
            }
            return false;
        }
        return false;
    }

    public void deleteRoleFromUser(String role_name, String user_name) {
        var role = roleService.findByName(role_name);
        if(role.isPresent()) {
            var user = userService.findByUsername(user_name);
            if(user.isPresent()) {
                var deleted = user_rolesService.findByUserIdAndRoleId(user.get().getId(),role.get().getId());
                for(var deleted_role : deleted) {
                    user_rolesService.deleteById((long)deleted_role.getId());
                }
            }
        }
    }

    public boolean checkHasPermission(String user_name, String permission_name) {
        // 获取用户
        var user = userService.findByUsername(user_name);
        if(user.isPresent()) {
            // 获取用户——角色关联
            var userRoles = user_rolesService.findByUserId(user.get().getId());
            for(var userRole : userRoles) {
                // 获取角色——权限关联
                var permissions = role_permissionService.findByRoleId(userRole.getRoleId());
                for(var permission:permissions){
                    // 获取权限，判断
                    var perm = permissionService.findById((long)permission.getPermissionId());
                    if(perm.isPresent()) {
                        if(perm.get().getPermissionName().equals(permission_name)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
