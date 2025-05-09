package com.wj2025.mobileclass.service.IService;

public interface IRoleService {
    boolean createRole(String name, String description);

    boolean updateRole(int id, String name, String description);

    boolean deleteRole(int id);

    boolean assignRoleToUser(int userId, int roleId);

    boolean removeRoleFromUser(int userId, int roleId);

    boolean assignPermissionToRole(int roleId, int permissionId);

    boolean removePermissionFromRole(int roleId, int permissionId);
}
