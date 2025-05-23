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

import java.util.List;

@Service
public class PermissionService {
    private final IPermissionService permissionService;
    private final IRoleService roleService;
    private final IRole_PermissionService role_permissionService;
    private final IUser_RolesService user_rolesService;
    private final IUserServiece userServiece;

    public PermissionService(IPermissionService permissionService,IRoleService roleService,IRole_PermissionService role_permissionService,IUser_RolesService user_rolesService,IUserServiece userServiece) {
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.role_permissionService = role_permissionService;
        this.user_rolesService = user_rolesService;
        this.userServiece = userServiece;
    }

    public boolean addPermission(String title, String description,String permission_name) {
        var permissions = permissionService.findBypermission_name(permission_name);
        if (permissions.isPresent()) {
            return false;
        }
        var permissionModel = new PermissionModel();
        permissionModel.setTitle(title);
        permissionModel.setDescription(description);
        permissionModel.setPermission_name(permission_name);
        permissionService.save(permissionModel);
        return true;
    }

    public boolean deletePermission(String permission_name) {
        var permissions = permissionService.findBypermission_name(permission_name);
        if (permissions.isPresent()) {
            permissionService.delete(permissions.get());
            return true;
        }
        return false;
    }

    public List<PermissionModel> getPermissionByTitle(String title) {
        return permissionService.getPermissionByTitle(title);
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
        var permission = permissionService.findBypermission_name(permission_name);
        if(permission.isPresent()) {
            var role = roleService.findByName(role_name);
            if(role.isPresent()) {
                var role_permission = new Role_PermissionModel();
                role_permission.setPermission_id(permission.get().getId());
                role_permission.setRole_id(role.get().getId());
                role_permissionService.save(role_permission);
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean deletePermissionFromRole(String role_name, String permission_name) {
        var permission = permissionService.findBypermission_name(permission_name);
        if(permission.isPresent()) {
            var role = roleService.findByName(role_name);
            if(role.isPresent()) {
                var role_permission = role_permissionService.findBypermission_idAndrole_id(permission.get().getId(),role.get().getId());
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
            var user = userServiece.findByUsername(user_name);
            if(user.isPresent()) {
                User_RolesModel user_rolesModel = new User_RolesModel();
                user_rolesModel.setRole_id(role.get().getId());
                user_rolesModel.setUser_id(user.get().getId());
                user_rolesService.save(user_rolesModel);
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean deleteRoleFromUser(String role_name, String user_name) {
        var role = roleService.findByName(role_name);
        if(role.isPresent()) {
            var user = userServiece.findByUsername(user_name);
            if(user.isPresent()) {
                user_rolesService.deleteByuser_idAndrole_id(user.get().getId(),role.get().getId());
                return true;
            }
            return false;
        }
        return false;
    }
}
