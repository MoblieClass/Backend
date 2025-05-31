package com.wj2025.mobileclass.model.permission;

import jakarta.persistence.*;

@Entity
@Table(name = "xw05_role_permission")
public class Role_PermissionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int roleId;
    private int permissionId;
    public int getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int role_id) {
        this.roleId = role_id;
    }
    public int getPermissionId() {
        return permissionId;
    }
    public void setPermissionId(int permission_id) {
        this.permissionId = permission_id;
    }
}
