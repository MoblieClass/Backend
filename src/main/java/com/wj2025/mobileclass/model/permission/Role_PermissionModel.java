package com.wj2025.mobileclass.model.permission;

import jakarta.persistence.*;

@Entity
@Table(name = "xw05_role_permission")
public class Role_PermissionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int role_id;
    private int permission_id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getRole_id() {
        return role_id;
    }
    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
    public int getPermission_id() {
        return permission_id;
    }
    public void setPermission_id(int permission_id) {
        this.permission_id = permission_id;
    }
}
