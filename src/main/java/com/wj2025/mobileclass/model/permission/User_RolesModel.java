package com.wj2025.mobileclass.model.permission;

import jakarta.persistence.*;

@Entity
@Table(name = "xw05_user_roles")
public class User_RolesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int roleId;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int user_id) {
        this.userId = user_id;
    }
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int role_id) {
        this.roleId = role_id;
    }
}
